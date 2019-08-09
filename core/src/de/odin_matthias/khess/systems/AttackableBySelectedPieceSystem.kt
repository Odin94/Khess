package de.odin_matthias.khess.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import de.odin_matthias.khess.components.AttackableComponent
import de.odin_matthias.khess.components.ColorComponent
import de.odin_matthias.khess.components.PieceSelectComponent
import de.odin_matthias.khess.components.PositionComponent
import de.odin_matthias.khess.components.movement.AttackComponent
import de.odin_matthias.khess.components.movement.colorToDirection
import de.odin_matthias.khess.game.GameConfig.BOARD_DIMENSION
import ktx.ashley.allOf
import ktx.ashley.mapperFor


object AttackableBySelectedPieceSystem : EntitySystem() {
    private lateinit var entities: ImmutableArray<Entity>

    private val position = mapperFor<PositionComponent>()
    private val selected = mapperFor<PieceSelectComponent>()
    private val attacker = mapperFor<AttackComponent>()
    private val attackable = mapperFor<AttackableComponent>()
    private val color = mapperFor<ColorComponent>()

    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(allOf(PieceSelectComponent::class).get())
    }

    override fun update(deltaTime: Float) {

    }

    fun onSelect() {
        entities.forEach {
            attackable.get(it).attackableBySelectedPiece = false
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

    private fun isWithinBounds(pos: Vector2): Boolean {
        return pos.x >= 0 && pos.y >= 0 && pos.x <= BOARD_DIMENSION && pos.y <= BOARD_DIMENSION
    }

    private fun markAttackableOpponentPieces(selectedPos: Vector2, originalDirectionVector: Vector2, opponentPieces: List<Entity>, distance: Int) {
        var field = selectedPos.add(originalDirectionVector)

        var travelledDistance = 1
        while (isWithinBounds(field) && travelledDistance <= distance) {
            val opponent = opponentPieces.firstOrNull {
                position.get(it).coordVector == Vector2(field).add(originalDirectionVector)
            }

            Gdx.app.log("", "[$originalDirectionVector] -> $field")
            field.add(originalDirectionVector)
            travelledDistance++
            if (opponent != null) {
                attackable.get(opponent).attackableBySelectedPiece = true
                break
            }
        }
    }

    private fun getSelectedPiece() = entities.firstOrNull { selected.get(it).selected }
}