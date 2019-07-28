package de.odin_matthias.khess.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.utils.ImmutableArray
import de.odin_matthias.khess.components.PieceSelectComponent
import de.odin_matthias.khess.components.PositionComponent
import ktx.ashley.allOf


class PieceSelectSystem : EntitySystem() {
    private lateinit var entities: ImmutableArray<Entity>

    private val positionMapper = ComponentMapper.getFor(PositionComponent::class.java)

    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(allOf(PieceSelectComponent::class).get())
    }

    override fun update(deltaTime: Float) {
        entities.forEach {
            val position = positionMapper.get(it)
        }
    }
}