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
    private lateinit var entities: ImmutableArray<Entity>

    private val position = mapperFor<PositionComponent>()
    private val attacker = mapperFor<AttackComponent>()
    private val attackable = mapperFor<AttackableComponent>()
    private val blocker = mapperFor<BlockerComponent>()
    private val color = mapperFor<ColorComponent>()

    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(allOf(PieceSelectComponent::class).get())
    }

    override fun update(deltaTime: Float) {

    }

    fun onSelect() {
        entities.forEach {
            attackable.get(it)?.attackableBySelectedPiece = false
        }

        getSelectedPiece()?.let {
            val selectedPos = position.get(it).coordVector
            val selectedPieceColor = color.get(it).color
            val opponentPieces = entities.filter { opponent -> color.get(opponent).color != selectedPieceColor }

            val movementMap = colorToDirection.getValue(selectedPieceColor)

            // TODO incorporate knight movement
            attacker.get(it)?.directions?.forEach { direction ->
                markAttackableOpponentPieces(selectedPos, movementMap.getValue(direction), opponentPieces, attacker.get(it).distance)
            }
        }
    }

    private fun markAttackableOpponentPieces(selectedPos: Vector2, originalDirectionVector: Vector2, opponentPieces: List<Entity>, distance: Int) {
        var field = Vector2(selectedPos).add(originalDirectionVector)

        var travelledDistance = 1
        while (isWithinBounds(field) && travelledDistance < distance) {
            val opponent = opponentPieces.firstOrNull {
                position.get(it).coordVector == Vector2(field).add(originalDirectionVector)
            }

            field.add(originalDirectionVector)
            travelledDistance++
            if (opponent != null) {
                attackable.get(opponent).attackableBySelectedPiece = true
                if (opponent.has(blocker)) break
            }
        }
    }
}