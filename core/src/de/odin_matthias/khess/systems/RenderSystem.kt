package de.odin_matthias.khess.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import de.odin_matthias.khess.Camera
import de.odin_matthias.khess.components.AttackableComponent
import de.odin_matthias.khess.components.PieceSelectComponent
import de.odin_matthias.khess.components.PositionComponent
import de.odin_matthias.khess.components.VisualComponent
import de.odin_matthias.khess.components.movement.WalkableComponent
import de.odin_matthias.khess.resources.TextureRepository.ATTACKABLE_HIGHLIGHT
import de.odin_matthias.khess.resources.TextureRepository.SELECTION_HIGHLIGHT
import de.odin_matthias.khess.resources.TextureRepository.WALKABLE_HIGHLIGHT
import ktx.ashley.allOf
import ktx.ashley.mapperFor


class RenderSystem(private val batch: SpriteBatch = SpriteBatch(), private val camera: OrthographicCamera = Camera) : EntitySystem() {
    private lateinit var entities: ImmutableArray<Entity>

    private val position = mapperFor<PositionComponent>()
    private val visual = mapperFor<VisualComponent>()
    private val pieceSelect = mapperFor<PieceSelectComponent>()
    private val attackable = mapperFor<AttackableComponent>()
    private val walkable = mapperFor<WalkableComponent>()


    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(allOf(PositionComponent::class, VisualComponent::class).get())
    }

    override fun removedFromEngine(engine: Engine) {
        addedToEngine(engine)
    }

    override fun update(deltaTime: Float) {
        camera.update()

        batch.projectionMatrix = camera.combined
        batch.begin()
        entities.forEach {
            val position = position.get(it)
            val visual = visual.get(it)

            batch.draw(visual.texture, position.x, position.y)

            if (pieceSelect.get(it)?.selected == true) {
                batch.draw(SELECTION_HIGHLIGHT, position.x, position.y)
            }
            if (attackable.get(it)?.attackableBySelectedPiece == true) {
                batch.draw(ATTACKABLE_HIGHLIGHT, position.x, position.y)
            }
            if (walkable.get(it)?.walkableBySelectedPiece == true) {
                batch.draw(WALKABLE_HIGHLIGHT, position.x, position.y)
            }
            if (walkable.get(it)?.castleableBy != null) {
                batch.draw(WALKABLE_HIGHLIGHT, position.x, position.y)
            }
        }
        batch.end()
    }
}