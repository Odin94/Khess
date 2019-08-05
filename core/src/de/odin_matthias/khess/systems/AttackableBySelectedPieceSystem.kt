package de.odin_matthias.khess.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.utils.ImmutableArray
import de.odin_matthias.khess.components.PieceSelectComponent
import de.odin_matthias.khess.components.PositionComponent
import de.odin_matthias.khess.components.movement.AttackComponent
import ktx.ashley.allOf
import ktx.ashley.mapperFor


class AttackableBySelectedPieceSystem : EntitySystem() {
    private lateinit var entities: ImmutableArray<Entity>

    private val position = mapperFor<PositionComponent>()
    private val selected = mapperFor<PieceSelectComponent>()
    private val attacker = mapperFor<AttackComponent>()

    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(allOf(PieceSelectComponent::class).get())
    }

    override fun update(deltaTime: Float) {
        val selectedPiece = getSelectedPiece()

        if (selectedPiece != null) {
            val selectedPos = position.get(selectedPiece)
            entities.filter { it != selectedPiece }.forEach {
                val pos = position.get(it)
                val attacker = attacker.get(it)

                // TODO: attacker.movementOptions.each(directionsToNumbersTop[it].each mult by distance, see what you hit))
            }
        }
    }

    private fun getSelectedPiece() = entities.firstOrNull { selected.get(it).selected }
}