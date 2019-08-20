package de.odin_matthias.khess

import com.badlogic.ashley.core.Engine
import de.odin_matthias.khess.components.*
import de.odin_matthias.khess.components.movement.*
import de.odin_matthias.khess.game.GameConfig.TILE_SIZE
import de.odin_matthias.khess.resources.TextureRepository.BLACK_BISHOP
import de.odin_matthias.khess.resources.TextureRepository.BLACK_KING
import de.odin_matthias.khess.resources.TextureRepository.BLACK_KNIGHT
import de.odin_matthias.khess.resources.TextureRepository.BLACK_PAWN
import de.odin_matthias.khess.resources.TextureRepository.BLACK_QUEEN
import de.odin_matthias.khess.resources.TextureRepository.BLACK_ROOK
import de.odin_matthias.khess.resources.TextureRepository.BLACK_TILE
import de.odin_matthias.khess.resources.TextureRepository.WHITE_BISHOP
import de.odin_matthias.khess.resources.TextureRepository.WHITE_KING
import de.odin_matthias.khess.resources.TextureRepository.WHITE_KNIGHT
import de.odin_matthias.khess.resources.TextureRepository.WHITE_PAWN
import de.odin_matthias.khess.resources.TextureRepository.WHITE_QUEEN
import de.odin_matthias.khess.resources.TextureRepository.WHITE_ROOK
import de.odin_matthias.khess.resources.TextureRepository.WHITE_TILE
import ktx.ashley.entity

enum class PieceColors {
    BLACK,
    WHITE
}

object EntityFactory {
    fun addTile(engine: Engine, row: Float, col: Float) =
            engine.entity {
                with<VisualComponent> {
                    texture = if (row % 2 == col % 2) BLACK_TILE else WHITE_TILE
                }
                with<PositionComponent> {
                    x = row * TILE_SIZE
                    y = col * TILE_SIZE
                }
                with<WalkableComponent>()
            }

    fun addPawn(engine: Engine, row: Float, col: Float, color: PieceColors) =
            engine.entity {
                with<VisualComponent> {
                    texture = when (color) {
                        PieceColors.BLACK -> BLACK_PAWN
                        PieceColors.WHITE -> WHITE_PAWN
                    }
                }
                with<PositionComponent> {
                    x = row * TILE_SIZE
                    y = col * TILE_SIZE
                }
                with<PieceSelectComponent>()
                with<BlockerComponent>()
                with<ColorComponent> { this.color = color }
                with<DirectMovementComponent> {
                    distance = 2
                    directions = listOf(Directions.FORWARD)
                }
                with<DistanceModifierComponent> { newDistance = 1 }
                with<AttackComponent> {
                    distance = 1
                    directions = listOf(Directions.FORWARD_LEFT, Directions.FORWARD_RIGHT)
                }
                with<AttackableComponent>()
                with<PromotionComponent>()
            }

    fun addRook(engine: Engine, row: Float, col: Float, color: PieceColors) =
            engine.entity {
                with<VisualComponent> {
                    texture = when (color) {
                        PieceColors.BLACK -> BLACK_ROOK
                        PieceColors.WHITE -> WHITE_ROOK
                    }
                }
                with<PositionComponent> {
                    x = row * TILE_SIZE
                    y = col * TILE_SIZE
                }
                with<PieceSelectComponent>()
                with<BlockerComponent>()
                with<ColorComponent> { this.color = color }
                with<DirectMovementComponent> {
                    distance = Integer.MAX_VALUE
                    directions = listOf(Directions.FORWARD, Directions.LEFT, Directions.RIGHT, Directions.BACKWARD)
                }
                with<AttackComponent> {
                    distance = Integer.MAX_VALUE
                    directions = listOf(Directions.FORWARD, Directions.LEFT, Directions.RIGHT, Directions.BACKWARD)
                }
                with<AttackableComponent>()
                with<CastlingTargetComponent>()
            }

    fun addKnight(engine: Engine, row: Float, col: Float, color: PieceColors) =
            engine.entity {
                with<VisualComponent> {
                    texture = when (color) {
                        PieceColors.BLACK -> BLACK_KNIGHT
                        PieceColors.WHITE -> WHITE_KNIGHT
                    }
                }
                with<PositionComponent> {
                    x = row * TILE_SIZE
                    y = col * TILE_SIZE
                }
                with<PieceSelectComponent>()
                with<BlockerComponent>()
                with<ColorComponent> { this.color = color }
                with<AttackableComponent>()
                with<JumpMovementComponent> { movements = knightMovements }
                with<AttackComponent> { jumpAttacks = knightMovements }
            }

    fun addBishop(engine: Engine, row: Float, col: Float, color: PieceColors) =
            engine.entity {
                with<VisualComponent> {
                    texture = when (color) {
                        PieceColors.BLACK -> BLACK_BISHOP
                        PieceColors.WHITE -> WHITE_BISHOP
                    }
                }
                with<PositionComponent> {
                    x = row * TILE_SIZE
                    y = col * TILE_SIZE
                }
                with<PieceSelectComponent>()
                with<BlockerComponent>()
                with<ColorComponent> { this.color = color }
                with<DirectMovementComponent> {
                    distance = Integer.MAX_VALUE
                    directions = listOf(Directions.FORWARD_LEFT, Directions.FORWARD_RIGHT, Directions.BACKWARD_RIGHT, Directions.BACKWARD_LEFT)
                }
                with<AttackComponent> {
                    distance = Integer.MAX_VALUE
                    directions = listOf(Directions.FORWARD_LEFT, Directions.FORWARD_RIGHT, Directions.BACKWARD_RIGHT, Directions.BACKWARD_LEFT)
                }
                with<AttackableComponent>()
            }

    fun addQueen(engine: Engine, row: Float, col: Float, color: PieceColors) =
            engine.entity {
                with<VisualComponent> {
                    texture = when (color) {
                        PieceColors.BLACK -> BLACK_QUEEN
                        PieceColors.WHITE -> WHITE_QUEEN
                    }
                }
                with<PositionComponent> {
                    x = row * TILE_SIZE
                    y = col * TILE_SIZE
                }
                with<PieceSelectComponent>()
                with<BlockerComponent>()
                with<ColorComponent> { this.color = color }
                with<DirectMovementComponent> {
                    distance = Integer.MAX_VALUE
                    directions = Directions.values().toList()
                }
                with<AttackComponent> {
                    distance = Integer.MAX_VALUE
                    directions = Directions.values().toList()
                }
                with<AttackableComponent>()
            }

    fun addKing(engine: Engine, row: Float, col: Float, color: PieceColors) =
            engine.entity {
                with<VisualComponent> {
                    texture = when (color) {
                        PieceColors.BLACK -> BLACK_KING
                        PieceColors.WHITE -> WHITE_KING
                    }
                }
                with<PositionComponent> {
                    x = row * TILE_SIZE
                    y = col * TILE_SIZE
                }
                with<PieceSelectComponent>()
                with<BlockerComponent>()
                with<ColorComponent> { this.color = color }
                with<DirectMovementComponent> {
                    distance = 1
                    directions = Directions.values().toList()
                }
                with<AttackComponent> {
                    distance = 1
                    directions = Directions.values().toList()
                }
                with<AttackableComponent>()
                with<CastlingComponent>()
            }
}