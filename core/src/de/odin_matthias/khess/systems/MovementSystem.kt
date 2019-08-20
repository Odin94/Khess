package de.odin_matthias.khess.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.math.Vector2
import de.odin_matthias.khess.Camera
import de.odin_matthias.khess.components.PieceSelectComponent
import de.odin_matthias.khess.components.PositionComponent
import de.odin_matthias.khess.components.movement.WalkableComponent
import de.odin_matthias.khess.extensions.component1
import de.odin_matthias.khess.extensions.component2
import de.odin_matthias.khess.extensions.getSelectedPiece
import de.odin_matthias.khess.extensions.isPointInTile
import ktx.ashley.allOf
import ktx.ashley.mapperFor


class MovementSystem : EntitySystem() {
    private lateinit var entities: ImmutableArray<Entity>
    private lateinit var walkables: ImmutableArray<Entity>

    private val position = mapperFor<PositionComponent>()
    private val walkable = mapperFor<WalkableComponent>()


    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(allOf(PieceSelectComponent::class, PieceSelectComponent::class, PositionComponent::class).get())
        walkables = engine.getEntitiesFor(allOf(WalkableComponent::class).get())
    }

    override fun removedFromEngine(engine: Engine) {
        addedToEngine(engine)
    }

    override fun update(deltaTime: Float) {

    }

    fun move(): Boolean {
        getSelectedPiece(engine)?.let { selected ->
            getSelectedWalkable()?.let { walkable ->
                position.get(selected).vector = position.get(walkable).vector

                triggerSystems(selected)

                return true
            }
        }

        return false
    }

    private fun triggerSystems(entity: Entity) {
        engine.getSystem(TurnSystem::class.java).nextTurn()
        engine.getSystem(CastleableBySelectedPieceSystem::class.java).onMoved(entity)

        engine.getSystem(DistanceModifierSystem::class.java).trigger(entity)
        engine.getSystem(WalkableBySelectedPieceSystem::class.java).trigger()
        engine.getSystem(AttackableBySelectedPieceSystem::class.java).trigger()
        engine.getSystem(CastleableBySelectedPieceSystem::class.java).trigger()
        engine.getSystem(PromotionSystem::class.java).trigger(entity)
    }

    private fun getSelectedWalkable(): Entity? {
        val (x, y) = Camera.getMousePosInGameWorld()

        return walkables.filter { walkable.get(it).walkableBySelectedPiece }.firstOrNull {
            isPointInTile(Vector2(x, y), position.get(it).vector)
        }
    }
}