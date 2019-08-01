package de.odin_matthias.khess

import com.badlogic.ashley.core.Engine
import de.odin_matthias.khess.components.BlockerComponent
import de.odin_matthias.khess.components.PieceSelectComponent
import de.odin_matthias.khess.components.PositionComponent
import de.odin_matthias.khess.components.VisualComponent
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

enum class PIECE_COLOR {
    BLACK,
    WHITE
}

object EntityFactory {
    fun addTile(engine: Engine, row: Float, col: Float) =
            engine.entity {
                with<VisualComponent> {
                    texture = if (row % 2 == col % 2) WHITE_TILE else BLACK_TILE
                }
                with<PositionComponent> {
                    x = row * TILE_SIZE
                    y = col * TILE_SIZE
                }
                with<BlockerComponent> {
                    this.color = color
                }
            }

    fun addPawn(engine: Engine, row: Float, col: Float, color: PIECE_COLOR) =
            engine.entity {
                with<VisualComponent> {
                    texture = when (color) {
                        PIECE_COLOR.BLACK -> BLACK_PAWN
                        PIECE_COLOR.WHITE -> WHITE_PAWN
                    }
                }
                with<PositionComponent> {
                    x = row * TILE_SIZE
                    y = col * TILE_SIZE
                }
                with<PieceSelectComponent>()
                with<BlockerComponent> {
                    this.color = color
                }
            }

    fun addRook(engine: Engine, row: Float, col: Float, color: PIECE_COLOR) =
            engine.entity {
                with<VisualComponent> {
                    texture = when (color) {
                        PIECE_COLOR.BLACK -> BLACK_ROOK
                        PIECE_COLOR.WHITE -> WHITE_ROOK
                    }
                }
                with<PositionComponent> {
                    x = row * TILE_SIZE
                    y = col * TILE_SIZE
                }
                with<PieceSelectComponent>()
                with<BlockerComponent> {
                    this.color = color
                }
            }

    fun addKnight(engine: Engine, row: Float, col: Float, color: PIECE_COLOR) =
            engine.entity {
                with<VisualComponent> {
                    texture = when (color) {
                        PIECE_COLOR.BLACK -> BLACK_KNIGHT
                        PIECE_COLOR.WHITE -> WHITE_KNIGHT
                    }
                }
                with<PositionComponent> {
                    x = row * TILE_SIZE
                    y = col * TILE_SIZE
                }
                with<PieceSelectComponent>()
                with<BlockerComponent> {
                    this.color = color
                }
            }

    fun addBishop(engine: Engine, row: Float, col: Float, color: PIECE_COLOR) =
            engine.entity {
                with<VisualComponent> {
                    texture = when (color) {
                        PIECE_COLOR.BLACK -> BLACK_BISHOP
                        PIECE_COLOR.WHITE -> WHITE_BISHOP
                    }
                }
                with<PositionComponent> {
                    x = row * TILE_SIZE
                    y = col * TILE_SIZE
                }
                with<PieceSelectComponent>()
                with<BlockerComponent> {
                    this.color = color
                }
            }

    fun addQueen(engine: Engine, row: Float, col: Float, color: PIECE_COLOR) =
            engine.entity {
                with<VisualComponent> {
                    texture = when (color) {
                        PIECE_COLOR.BLACK -> BLACK_QUEEN
                        PIECE_COLOR.WHITE -> WHITE_QUEEN
                    }
                }
                with<PositionComponent> {
                    x = row * TILE_SIZE
                    y = col * TILE_SIZE
                }
                with<PieceSelectComponent>()
                with<BlockerComponent> {
                    this.color = color
                }
            }

    fun addKing(engine: Engine, row: Float, col: Float, color: PIECE_COLOR) =
            engine.entity {
                with<VisualComponent> {
                    texture = when (color) {
                        PIECE_COLOR.BLACK -> BLACK_KING
                        PIECE_COLOR.WHITE -> WHITE_KING
                    }
                }
                with<PositionComponent> {
                    x = row * TILE_SIZE
                    y = col * TILE_SIZE
                }
                with<PieceSelectComponent>()
                with<BlockerComponent> {
                    this.color = color
                }
            }
}