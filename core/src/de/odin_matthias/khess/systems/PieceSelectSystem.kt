package de.odin_matthias.khess.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import de.odin_matthias.khess.components.PieceSelectComponent
import de.odin_matthias.khess.components.PositionComponent
import de.odin_matthias.khess.game.GameConfig.BOARD_SIZE
import de.odin_matthias.khess.game.GameConfig.TILE_SIZE
import ktx.ashley.allOf
import ktx.ashley.mapperFor


object PieceSelectSystem : EntitySystem() {
    private lateinit var entities: ImmutableArray<Entity>

    private val position = mapperFor<PositionComponent>()
    private val selected = mapperFor<PieceSelectComponent>()

    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(allOf(PieceSelectComponent::class).get())
    }

    override fun update(deltaTime: Float) {

    }

    fun select(clickX: Int, clickY: Int): Boolean {
        var selectedAPiece = false
        entities.forEach {
            selected.get(it).selected = clickHitPiece(it, clickX, clickY)
            if (selected.get(it).selected) selectedAPiece = true
        }

        return selectedAPiece
    }

    private fun clickHitPiece(piece: Entity, clickX: Int, clickY: Int): Boolean {
        if (!Gdx.input.isTouched) return false

        val piecePos = position.get(piece)
        val y = BOARD_SIZE - clickY

        return clickX > piecePos.x && clickX < piecePos.x + TILE_SIZE && y > piecePos.y && y < piecePos.y + TILE_SIZE
    }
}