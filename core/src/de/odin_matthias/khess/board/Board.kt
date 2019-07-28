package de.odin_matthias.khess.board

import com.badlogic.ashley.core.Engine
import de.odin_matthias.khess.EntityFactory.addBishop
import de.odin_matthias.khess.EntityFactory.addKing
import de.odin_matthias.khess.EntityFactory.addKnight
import de.odin_matthias.khess.EntityFactory.addPawn
import de.odin_matthias.khess.EntityFactory.addQueen
import de.odin_matthias.khess.EntityFactory.addRook
import de.odin_matthias.khess.EntityFactory.addTile
import de.odin_matthias.khess.PIECE_COLOR
import de.odin_matthias.khess.systems.RenderSystem


class Board(private val width: Int, private val height: Int) {
    val engine = Engine()

    init {
        generateEntities()
        generateSystems()
    }

    private fun generateEntities() {
        generateTiles(width, height)
        generateBlackPieces()
        generateWhitePieces()
    }

    private fun generateSystems() {
        engine.addSystem(RenderSystem())
    }

    private fun generateTiles(width: Int, height: Int) = also {
        for (row in 0 until width) {
            for (col in 0 until height) {
                addTile(engine, row.toFloat(), col.toFloat())
            }
        }
    }

    private fun generateBlackPieces() {
        for (row in 0 until width) {
            addPawn(engine, row.toFloat(), 1f, PIECE_COLOR.BLACK)
        }

        addRook(engine, 0f, 0f, PIECE_COLOR.BLACK)
        addRook(engine, 7f, 0f, PIECE_COLOR.BLACK)

        addKnight(engine, 1f, 0f, PIECE_COLOR.BLACK)
        addKnight(engine, 6f, 0f, PIECE_COLOR.BLACK)

        addBishop(engine, 2f, 0f, PIECE_COLOR.BLACK)
        addBishop(engine, 5f, 0f, PIECE_COLOR.BLACK)

        addQueen(engine, 3f, 0f, PIECE_COLOR.BLACK)
        addKing(engine, 4f, 0f, PIECE_COLOR.BLACK)
    }

    private fun generateWhitePieces() {
        for (row in 0 until width) {
            addPawn(engine, row.toFloat(), 6f, PIECE_COLOR.WHITE)
        }

        addRook(engine, 0f, 7f, PIECE_COLOR.WHITE)
        addRook(engine, 7f, 7f, PIECE_COLOR.WHITE)

        addKnight(engine, 1f, 7f, PIECE_COLOR.WHITE)
        addKnight(engine, 6f, 7f, PIECE_COLOR.WHITE)

        addBishop(engine, 2f, 7f, PIECE_COLOR.WHITE)
        addBishop(engine, 5f, 7f, PIECE_COLOR.WHITE)

        addQueen(engine, 3f, 7f, PIECE_COLOR.WHITE)
        addKing(engine, 4f, 7f, PIECE_COLOR.WHITE)
    }
}