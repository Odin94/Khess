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


class PieceSelectSystem : EntitySystem() {
    private lateinit var entities: ImmutableArray<Entity>

    private val position = mapperFor<PositionComponent>()
    private val selected = mapperFor<PieceSelectComponent>()

    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(allOf(PieceSelectComponent::class).get())
    }

    override fun update(deltaTime: Float) {
        entities.forEach {
            selected.get(it).selected = clickHitPiece(it)
        }
    }

    private fun clickHitPiece(piece: Entity): Boolean {
        if (!Gdx.input.isTouched) return false

        val piecePos = position.get(piece)
        val x = Gdx.input.x
        val y = BOARD_SIZE - Gdx.input.y

        return x >= piecePos.x && x <= piecePos.x + TILE_SIZE && y >= piecePos.y && y <= piecePos.y + TILE_SIZE
    }
}