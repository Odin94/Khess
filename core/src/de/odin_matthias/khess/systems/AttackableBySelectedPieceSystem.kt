package de.odin_matthias.khess.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.math.Vector2
import de.odin_matthias.khess.components.*
import de.odin_matthias.khess.components.movement.AttackComponent
import de.odin_matthias.khess.components.movement.colorToDirection
import de.odin_matthias.khess.extensions.getSelectedPiece
import de.odin_matthias.khess.extensions.isWithinBounds
import ktx.ashley.allOf
import ktx.ashley.mapperFor


class AttackableBySelectedPieceSystem : EntitySystem() {
    private lateinit var attackablePieces: ImmutableArray<Entity>
    private lateinit var blockers: ImmutableArray<Entity>

    private val position = mapperFor<PositionComponent>()
    private val attacker = mapperFor<AttackComponent>()
    private val attackable = mapperFor<AttackableComponent>()
    private val color = mapperFor<ColorComponent>()

    override fun addedToEngine(engine: Engine) {
        attackablePieces = engine.getEntitiesFor(allOf(PieceSelectComponent::class, AttackableComponent::class).get())
        blockers = engine.getEntitiesFor(allOf(BlockerComponent::class).get())
    }

    override fun removedFromEngine(engine: Engine) {
        addedToEngine(engine)
    }

    override fun update(deltaTime: Float) {

    }

    fun trigger() {
        attackablePieces.forEach {
            attackable.get(it).attackableBySelectedPiece = false
        }

        getSelectedPiece(engine)?.let {
            val selectedPos = position.get(it).coordVector
            val selectedPieceColor = color.get(it).color
            if (selectedPieceColor != engine.getSystem(TurnSystem::class.java).color) return

            val opponentPieces = attackablePieces.filter { opponent -> color.get(opponent).color != selectedPieceColor }

            val movementMap = colorToDirection.getValue(selectedPieceColor)

            attacker.get(it)?.directions?.forEach { direction ->
                markDirectlyAttackableOpponentPieces(selectedPos, movementMap.getValue(direction), opponentPieces, attacker.get(it).distance)
            }
            attacker.get(it)?.jumpAttacks?.forEach { attackVector ->
                markJumpinglyAttackableOpponentPieces(selectedPos, attackVector, opponentPieces)
            }
        }
    }

    private fun markJumpinglyAttackableOpponentPieces(selectedPos: Vector2, directionVector: Vector2,
                                                      opponentPieces: List<Entity>) {
        val field = Vector2(selectedPos).add(directionVector)
        if (isWithinBounds(field)) {
            opponentPieces.firstOrNull { position.get(it).coordVector == field }
                    ?.let { opponent -> attackable.get(opponent).attackableBySelectedPiece = true }
        }
    }

    private fun markDirectlyAttackableOpponentPieces(selectedPos: Vector2, directionVector: Vector2,
                                                     opponentPieces: List<Entity>, distance: Int) {
        val field = Vector2(selectedPos)

        var travelledDistance = 0
        while (isWithinBounds(field) && travelledDistance < distance) {
            field.add(directionVector)
            travelledDistance++

            val opponent = opponentPieces.firstOrNull {
                position.get(it).coordVector == field
            }

            if (opponent != null) {
                attackable.get(opponent).attackableBySelectedPiece = true
            }

            if (blockers.any { position.get(it).coordVector == field })
                break
        }
    }
}