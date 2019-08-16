package de.odin_matthias.khess.board

import com.badlogic.ashley.core.Engine
import de.odin_matthias.khess.EntityFactory.addBishop
import de.odin_matthias.khess.EntityFactory.addKing
import de.odin_matthias.khess.EntityFactory.addKnight
import de.odin_matthias.khess.EntityFactory.addPawn
import de.odin_matthias.khess.EntityFactory.addQueen
import de.odin_matthias.khess.EntityFactory.addRook
import de.odin_matthias.khess.EntityFactory.addTile
import de.odin_matthias.khess.PieceColors
import de.odin_matthias.khess.systems.*


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
        // TODO: turn systems into classes and get them across systems with engine.getSystem(system::class)
        engine.addSystem(PieceSelectSystem)
        engine.addSystem(AttackableBySelectedPieceSystem)
        engine.addSystem(WalkableBySelectedPieceSystem)
        engine.addSystem(MovementSystem)
        engine.addSystem(AttackSystem)
        engine.addSystem(DistanceModifierSystem)
        engine.addSystem(TurnSystem)
        engine.addSystem(CastleableBySelectedPieceSystem)
        engine.addSystem(CastlingSystem)
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
            addPawn(engine, row.toFloat(), 1f, PieceColors.BLACK)
        }

        addRook(engine, 0f, 0f, PieceColors.BLACK)
        addRook(engine, 7f, 0f, PieceColors.BLACK)

        addKnight(engine, 1f, 0f, PieceColors.BLACK)
        addKnight(engine, 6f, 0f, PieceColors.BLACK)

        addBishop(engine, 2f, 0f, PieceColors.BLACK)
        addBishop(engine, 5f, 0f, PieceColors.BLACK)

        addKing(engine, 3f, 0f, PieceColors.BLACK)
        addQueen(engine, 4f, 0f, PieceColors.BLACK)
    }

    private fun generateWhitePieces() {
        for (row in 0 until width) {
            addPawn(engine, row.toFloat(), 6f, PieceColors.WHITE)
        }

        addRook(engine, 0f, 7f, PieceColors.WHITE)
        addRook(engine, 7f, 7f, PieceColors.WHITE)

        addKnight(engine, 1f, 7f, PieceColors.WHITE)
        addKnight(engine, 6f, 7f, PieceColors.WHITE)

        addBishop(engine, 2f, 7f, PieceColors.WHITE)
        addBishop(engine, 5f, 7f, PieceColors.WHITE)

        addKing(engine, 3f, 7f, PieceColors.WHITE)
        addQueen(engine, 4f, 7f, PieceColors.WHITE)
    }
}