package de.odin_matthias.khess.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import de.odin_matthias.khess.components.PositionComponent
import de.odin_matthias.khess.components.VisualComponent
import de.odin_matthias.khess.game.GameConfig.BOARD_SIZE
import ktx.ashley.allOf


class RenderSystem(private val batch: SpriteBatch = SpriteBatch(), private val camera: OrthographicCamera = createCamera()) : EntitySystem() {
    private lateinit var entities: ImmutableArray<Entity>

    private val positionMapper = ComponentMapper.getFor(PositionComponent::class.java)
    private val visualMapper = ComponentMapper.getFor(VisualComponent::class.java)

    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(allOf(PositionComponent::class, VisualComponent::class).get())
    }

    override fun update(deltaTime: Float) {
        camera.update()

        batch.projectionMatrix = camera.combined
        batch.begin()
        entities.forEach {
            val position = positionMapper.get(it)
            val visual = visualMapper.get(it)

            batch.draw(visual.texture, position.x, position.y)
        }
        batch.end()
    }
}

private fun createCamera(): OrthographicCamera {
    val camera = OrthographicCamera(BOARD_SIZE.toFloat(), BOARD_SIZE.toFloat())
    camera.position.set(BOARD_SIZE.toFloat() / 2, BOARD_SIZE.toFloat() / 2, 0f)
    camera.zoom = 1.1f
    camera.update()

    return camera
}