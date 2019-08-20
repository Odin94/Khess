package de.odin_matthias.khess.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.math.Vector2
import de.odin_matthias.khess.Camera
import de.odin_matthias.khess.components.PieceSelectComponent
import de.odin_matthias.khess.components.PositionComponent
import de.odin_matthias.khess.components.movement.Castlers
import de.odin_matthias.khess.components.movement.WalkableComponent
import de.odin_matthias.khess.extensions.component1
import de.odin_matthias.khess.extensions.component2
import de.odin_matthias.khess.extensions.getSelectedPiece
import de.odin_matthias.khess.extensions.isPointInTile
import ktx.ashley.allOf
import ktx.ashley.mapperFor


class CastlingSystem : EntitySystem() {
    private lateinit var entities: ImmutableArray<Entity>
    private lateinit var tiles: ImmutableArray<Entity>


    private val position = mapperFor<PositionComponent>()
    private val walkable = mapperFor<WalkableComponent>()


    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(allOf(PieceSelectComponent::class, PositionComponent::class).get())
        tiles = engine.getEntitiesFor(allOf(WalkableComponent::class).get())
    }

    override fun removedFromEngine(engine: Engine) {
        addedToEngine(engine)
    }

    override fun update(deltaTime: Float) {

    }

    fun castle(): Boolean {
        getSelectedPiece(engine)?.let { selected ->
            getSelectedCastlers()?.let { castlers ->
                val castlerPosition = position.get(castlers.castler)
                val castleTargetPosition = position.get(castlers.castleTarget)

                if (castlerPosition.coordX > castleTargetPosition.coordX) {
                    castlerPosition.coordX = castlerPosition.coordX - 2
                    castleTargetPosition.coordX = castlerPosition.coordX + 1
                } else {
                    castlerPosition.coordX = castlerPosition.coordX + 2
                    castleTargetPosition.coordX = castlerPosition.coordX - 1
                }

                triggerSystems(selected)

                return true
            }
        }

        return false
    }

    private fun getSelectedCastlers(): Castlers? {
        val (x, y) = Camera.getMousePosInGameWorld()

        val castlersTile = tiles.filter { walkable.get(it).castleableBy != null }.firstOrNull {
            isPointInTile(Vector2(x, y), position.get(it).vector)
        }

        return if (castlersTile != null) walkable.get(castlersTile)?.castleableBy else null
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
}