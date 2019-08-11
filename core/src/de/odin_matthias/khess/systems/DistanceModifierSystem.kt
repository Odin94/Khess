package de.odin_matthias.khess.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.utils.ImmutableArray
import de.odin_matthias.khess.components.DistanceModifierComponent
import de.odin_matthias.khess.components.movement.DirectMovementComponent
import de.odin_matthias.khess.components.movement.JumpMovementComponent
import ktx.ashley.allOf
import ktx.ashley.has
import ktx.ashley.mapperFor


object DistanceModifierSystem : EntitySystem() {
    private lateinit var entities: ImmutableArray<Entity>

    private val distanceModifier = mapperFor<DistanceModifierComponent>()
    private val directMover = mapperFor<DirectMovementComponent>()
    private val jumpMover = mapperFor<JumpMovementComponent>()


    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(allOf(DistanceModifierComponent::class).get())
    }

    override fun removedFromEngine(engine: Engine) {
        addedToEngine(engine)
    }

    fun trigger(entity: Entity) {
        if (entity.has(distanceModifier)) {
            val newDistance = distanceModifier.get(entity).newDistance
            if (entity.has(directMover)) directMover.get(entity).distance = newDistance

            // TODO: Add jumpMover
        }
    }
}