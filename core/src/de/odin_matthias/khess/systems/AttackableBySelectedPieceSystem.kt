package de.odin_matthias.khess.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.math.Vector2
import de.odin_matthias.khess.components.*
import de.odin_matthias.khess.components.movement.AttackComponent
import de.odin_matthias.khess.components.movement.colorToDirection
import de.odin_matthias.khess.extensions.isWithinBounds
import de.odin_matthias.khess.systems.PieceSelectSystem.getSelectedPiece
import ktx.ashley.allOf
import ktx.ashley.has
import ktx.ashley.mapperFor


object AttackableBySelectedPieceSystem : EntitySystem() {
    private lateinit var attackablePieces: ImmutableArray<Entity>
    private lateinit var blockers: ImmutableArray<Entity>

    private val position = mapperFor<PositionComponent>()
    private val attacker = mapperFor<AttackComponent>()
    private val attackable = mapperFor<AttackableComponent>()
    private val blocker = mapperFor<BlockerComponent>()
    private val color = mapperFor<ColorComponent>()

    override fun addedToEngine(engine: Engine) {
        attackablePieces = engine.getEntitiesFor(allOf(PieceSelectComponent::class, AttackableComponent::class).get())
        blockers = engine.getEntitiesFor(allOf(BlockerComponent::class).get())
    }

    override fun update(deltaTime: Float) {

    }

    fun trigger() {
        attackablePieces.forEach {
            attackable.get(it).attackableBySelectedPiece = false
        }

        getSelectedPiece()?.let {
            val selectedPos = position.get(it).coordVector
            val selectedPieceColor = color.get(it).color
            val opponentPieces = attackablePieces.filter { opponent -> color.get(opponent).color != selectedPieceColor }

            val movementMap = colorToDirection.getValue(selectedPieceColor)

            // TODO incorporate knight movement
            attacker.get(it)?.directions?.forEach { direction ->
                markAttackableOpponentPieces(selectedPos, movementMap.getValue(direction), opponentPieces, attacker.get(it).distance)
            }
        }
    }

    private fun markAttackableOpponentPieces(selectedPos: Vector2, originalDirectionVector: Vector2, opponentPieces: List<Entity>, distance: Int) {
        var travelledDistance = 0
        while (isWithinBounds(selectedPos) && travelledDistance < distance) {
            selectedPos.add(originalDirectionVector)
            travelledDistance++

            val opponent = opponentPieces.firstOrNull {
                position.get(it).coordVector == selectedPos
            }

            if (blockers.any { position.get(it).coordVector == selectedPos })
                break

            if (opponent != null) {
                attackable.get(opponent).attackableBySelectedPiece = true
                if (opponent.has(blocker)) break
            }
        }
    }
}