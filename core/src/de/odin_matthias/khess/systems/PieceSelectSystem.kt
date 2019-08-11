package de.odin_matthias.khess.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.math.Vector2
import de.odin_matthias.khess.Camera
import de.odin_matthias.khess.components.PieceSelectComponent
import de.odin_matthias.khess.components.PositionComponent
import de.odin_matthias.khess.extensions.component1
import de.odin_matthias.khess.extensions.component2
import de.odin_matthias.khess.extensions.isPointInTile
import ktx.ashley.allOf
import ktx.ashley.mapperFor


object PieceSelectSystem : EntitySystem() {
    private lateinit var entities: ImmutableArray<Entity>

    private val position = mapperFor<PositionComponent>()
    private val selected = mapperFor<PieceSelectComponent>()


    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(allOf(PieceSelectComponent::class, PositionComponent::class).get())
    }

    override fun removedFromEngine(engine: Engine) {
        addedToEngine(engine)
    }

    override fun update(deltaTime: Float) {

    }

    fun select(): Boolean {
        var selectedAPiece = false
        entities.forEach {
            selected.get(it).selected = clickHitPiece(it)
            if (selected.get(it).selected) {
                selectedAPiece = true
            }
        }

        triggerSystems()
        return selectedAPiece
    }

    private fun triggerSystems() {
        // TODO: use signals for this?
        WalkableBySelectedPieceSystem.trigger()
        AttackableBySelectedPieceSystem.trigger()
    }

    private fun clickHitPiece(piece: Entity): Boolean {
        val piecePos = position.get(piece)
        val (x, y) = Camera.getMousePosInGameWorld()

        return isPointInTile(Vector2(x, y), piecePos.vector)
    }

    fun getSelectedPiece() = entities.firstOrNull { selected.get(it).selected }
}