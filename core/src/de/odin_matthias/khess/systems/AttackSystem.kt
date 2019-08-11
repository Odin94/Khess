package de.odin_matthias.khess.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.math.Vector2
import de.odin_matthias.khess.Camera
import de.odin_matthias.khess.components.AttackableComponent
import de.odin_matthias.khess.components.PieceSelectComponent
import de.odin_matthias.khess.components.PositionComponent
import de.odin_matthias.khess.extensions.component1
import de.odin_matthias.khess.extensions.component2
import de.odin_matthias.khess.extensions.isPointInTile
import de.odin_matthias.khess.systems.PieceSelectSystem.getSelectedPiece
import ktx.ashley.allOf
import ktx.ashley.mapperFor


object AttackSystem : EntitySystem() {
    private lateinit var entities: ImmutableArray<Entity>
    private lateinit var attackables: ImmutableArray<Entity>

    private val position = mapperFor<PositionComponent>()
    private val attackable = mapperFor<AttackableComponent>()


    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(allOf(PieceSelectComponent::class, PieceSelectComponent::class, PositionComponent::class).get())
        attackables = engine.getEntitiesFor(allOf(AttackableComponent::class).get())
    }

    override fun removedFromEngine(engine: Engine) {
        addedToEngine(engine)
    }

    override fun update(deltaTime: Float) {

    }

    fun attack(): Boolean {
        getSelectedPiece()?.let { selected ->
            getSelectedAttackable()?.let { attackable ->
                position.get(selected).vector = position.get(attackable).vector
                engine.removeEntity(attackable)

                triggerSystems(selected)

                return true
            }
        }

        return false
    }

    private fun triggerSystems(entity: Entity) {
        DistanceModifierSystem.trigger(entity)
        WalkableBySelectedPieceSystem.trigger()
        AttackableBySelectedPieceSystem.trigger()
    }

    private fun getSelectedAttackable(): Entity? {
        val (x, y) = Camera.getMousePosInGameWorld()

        return attackables.filter { attackable.get(it).attackableBySelectedPiece }.firstOrNull {
            isPointInTile(Vector2(x, y), position.get(it).vector)
        }
    }
}