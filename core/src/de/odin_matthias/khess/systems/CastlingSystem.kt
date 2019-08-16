package de.odin_matthias.khess.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.math.Vector2
import de.odin_matthias.khess.components.BlockerComponent
import de.odin_matthias.khess.components.ColorComponent
import de.odin_matthias.khess.components.PositionComponent
import de.odin_matthias.khess.components.movement.AttackComponent
import de.odin_matthias.khess.components.movement.CastlingComponent
import de.odin_matthias.khess.components.movement.CastlingTargetComponent
import de.odin_matthias.khess.components.movement.colorToDirection
import de.odin_matthias.khess.extensions.isWithinBounds
import ktx.ashley.allOf
import ktx.ashley.has
import ktx.ashley.mapperFor
import java.lang.Float.max
import java.lang.Float.min


object CastlingSystem : EntitySystem() {
    private lateinit var blockers: ImmutableArray<Entity>

    private val castler = mapperFor<CastlingComponent>()
    private val position = mapperFor<PositionComponent>()
    private val color = mapperFor<ColorComponent>()
    private val attacker = mapperFor<AttackComponent>()


    override fun addedToEngine(engine: Engine) {
        blockers = engine.getEntitiesFor(allOf(BlockerComponent::class).get())
    }

    override fun removedFromEngine(engine: Engine) {
        addedToEngine(engine)
    }

    override fun update(deltaTime: Float) {

    }

    fun onMoved(entity: Entity) {
        entity.remove(CastlingComponent::class.java)
        entity.remove(CastlingTargetComponent::class.java)
    }

    fun canCastle(castlingPiece: Entity) {
        if (castlingPiece.has(castler)) {
            val castleTargets = engine.getEntitiesFor(allOf(CastlingTargetComponent::class).get())
            val castlingPiecePos = position.get(castlingPiece)

            castleTargets
                    .filter { color.get(it).color == color.get(castlingPiece).color }
                    .filter { position.get(it).y == castlingPiecePos.y }
                    .filter { hasUnblockedHorizontalPath(position.get(it).coordVector, castlingPiecePos.coordVector) }
                    .filter { hasUnattackedHorizontalPath(it, castlingPiece) }
        }
    }

    private fun hasUnblockedHorizontalPath(pos1: Vector2, pos2: Vector2): Boolean {
        val upper = max(pos1.x, pos2.x) - 1
        val lower = min(pos1.x, pos2.x) + 1

        return blockers.firstOrNull {
            (lower..upper).contains(position.get(it).x)
        } == null
    }

    private fun hasUnattackedHorizontalPath(piece1: Entity, piece2: Entity): Boolean {
        val pieceColor = color.get(piece1).color
        val upper = max(position.get(piece1).x, position.get(piece2).x) - 1
        val lower = min(position.get(piece1).x, position.get(piece2).x) + 1

        engine.getEntitiesFor(allOf(AttackComponent::class).get())
                .filter { color.get(it).color != pieceColor }
                .forEach {
                    val positions = getAttackablePositions(it)
                    if (positions.any { pos -> (lower..upper).contains(pos.x) }) return false
                }

        return true
    }

    private fun getAttackablePositions(attackingPiece: Entity): Set<Vector2> {
        val attackablePositions = mutableSetOf<Vector2>()
        val pos = position.get(attackingPiece).coordVector
        val color = color.get(attackingPiece).color

        val movementMap = colorToDirection.getValue(color)

        val directlyAttackables = attacker.get(attackingPiece)?.directions?.flatMap { direction ->
            getDirectlyAttackablePositions(pos, movementMap.getValue(direction), attacker.get(attackingPiece).distance)
        }
        if (directlyAttackables != null) attackablePositions.addAll(directlyAttackables)

        val jumptinglyAttackables = attacker.get(attackingPiece)?.jumpAttacks?.filter { isWithinBounds(Vector2(pos).add(it)) }
        if (jumptinglyAttackables != null) attackablePositions.addAll(jumptinglyAttackables)


        return attackablePositions
    }

    private fun getDirectlyAttackablePositions(selectedPos: Vector2, directionVector: Vector2, distance: Int): Set<Vector2> {
        val field = Vector2(selectedPos)
        val attackablePositions = mutableSetOf<Vector2>()

        var travelledDistance = 0
        while (isWithinBounds(field) && travelledDistance < distance) {
            field.add(directionVector)
            travelledDistance++

            if (blockers.any { position.get(it).coordVector == field })
                break

            attackablePositions.add(Vector2(field))
        }

        return attackablePositions
    }
}