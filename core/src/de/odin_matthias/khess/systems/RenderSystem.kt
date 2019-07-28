package de.odin_matthias.khess.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import de.odin_matthias.khess.components.PositionComponent
import de.odin_matthias.khess.components.VisualComponent
import ktx.ashley.allOf


class RenderSystem(private val batch: SpriteBatch = SpriteBatch()) : EntitySystem() {
    private lateinit var entities: ImmutableArray<Entity>

    private val positionMapper = ComponentMapper.getFor(PositionComponent::class.java)
    private val visualMapper = ComponentMapper.getFor(VisualComponent::class.java)

    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(allOf(PositionComponent::class, VisualComponent::class).get())
    }

    override fun update(deltaTime: Float) {
        batch.begin()
        entities.forEach {
            val position = positionMapper.get(it)
            val visual = visualMapper.get(it)

            batch.draw(visual.texture, position.x, position.y)
        }
        batch.end()
    }
}