package de.odin_matthias.khess.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.math.Vector2
import de.odin_matthias.khess.components.BlockerComponent
import de.odin_matthias.khess.components.ColorComponent
import de.odin_matthias.khess.components.PieceSelectComponent
import de.odin_matthias.khess.components.PositionComponent
import de.odin_matthias.khess.components.movement.DirectMovementComponent
import de.odin_matthias.khess.components.movement.WalkableComponent
import de.odin_matthias.khess.components.movement.colorToDirection
import de.odin_matthias.khess.extensions.isWithinBounds
import de.odin_matthias.khess.systems.PieceSelectSystem.getSelectedPiece
import ktx.ashley.allOf
import ktx.ashley.mapperFor


object WalkableBySelectedPieceSystem : EntitySystem() {
    private lateinit var entities: ImmutableArray<Entity>
    private lateinit var blockers: ImmutableArray<Entity>
    private lateinit var tiles: ImmutableArray<Entity>

    private val color = mapperFor<ColorComponent>()
    private val position = mapperFor<PositionComponent>()
    private val blocker = mapperFor<BlockerComponent>()
    private val directMover = mapperFor<DirectMovementComponent>()
    private val walkable = mapperFor<WalkableComponent>()

    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(allOf(PieceSelectComponent::class).get())
        blockers = engine.getEntitiesFor(allOf(BlockerComponent::class).get())
        tiles = engine.getEntitiesFor(allOf(WalkableComponent::class).get())
    }

    override fun update(deltaTime: Float) {

    }

    fun trigger() {
        tiles.forEach {
            walkable.get(it)?.walkableBySelectedPiece = false
        }

        getSelectedPiece()?.let {
            val selectedPos = position.get(it).coordVector
            val selectedPieceColor = color.get(it).color

            val movementMap = colorToDirection.getValue(selectedPieceColor)

            // TODO incorporate knight movement
            directMover.get(it)?.directions?.forEach { direction ->
                markWalkableTiles(selectedPos, movementMap.getValue(direction), tiles, blockers, directMover.get(it).distance)
            }
        }
    }


    private fun markWalkableTiles(selectedPos: Vector2, originalDirectionVector: Vector2,
                                  tiles: ImmutableArray<Entity>, blockers: ImmutableArray<Entity>, distance: Int) {
        var field = selectedPos

        var travelledDistance = 0
        while (isWithinBounds(field) && travelledDistance < distance) {
            field.add(originalDirectionVector)
            travelledDistance++

            val tile = tiles.firstOrNull {
                position.get(it).coordVector == field
            }

            if (blockers.any { position.get(it).coordVector == field })
                break

            if (tile != null) walkable.get(tile).walkableBySelectedPiece = true
        }
    }
}