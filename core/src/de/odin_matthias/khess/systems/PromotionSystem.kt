package de.odin_matthias.khess.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import de.odin_matthias.khess.EntityFactory
import de.odin_matthias.khess.PieceColors
import de.odin_matthias.khess.components.ColorComponent
import de.odin_matthias.khess.components.PositionComponent
import de.odin_matthias.khess.components.PromotionComponent
import de.odin_matthias.khess.game.GameConfig
import ktx.ashley.mapperFor


object PromotionSystem : EntitySystem() {
    private val position = mapperFor<PositionComponent>()
    private val promotion = mapperFor<PromotionComponent>()
    private val color = mapperFor<ColorComponent>()

    fun trigger(entity: Entity): Boolean {
        if (!promotion.has(entity)) return false

        if (isOnOtherSide(entity)) {
            promote(entity)
            return true
        }

        return false
    }

    private fun isOnOtherSide(entity: Entity): Boolean {
        val yPos = position.get(entity).coordY
        val color = color.get(entity).color

        return if (color == PieceColors.WHITE) yPos == 0f else yPos == GameConfig.BOARD_DIMENSION.toFloat() - 1
    }

    private fun promote(entity: Entity) {
        val pos = position.get(entity).coordVector
        val color = color.get(entity).color

        engine.removeEntity(entity)
        EntityFactory.addQueen(engine, pos.x, pos.y, color)
    }
}