package de.odin_matthias.khess.resources

import com.badlogic.gdx.graphics.Texture


object TextureRepository {
    private const val pathPrefix = "data"
    private const val piecesPathPrefix = "pieces"

    lateinit var BLACK_TILE: Texture
    lateinit var WHITE_TILE: Texture

    lateinit var SELECTION_HIGHLIGHT: Texture
    lateinit var ATTACKABLE_HIGHLIGHT: Texture
    lateinit var MOVABLE_HIGHLIGHT: Texture


    lateinit var WHITE_PAWN: Texture
    lateinit var WHITE_ROOK: Texture
    lateinit var WHITE_BISHOP: Texture
    lateinit var WHITE_KNIGHT: Texture
    lateinit var WHITE_QUEEN: Texture
    lateinit var WHITE_KING: Texture

    lateinit var BLACK_PAWN: Texture
    lateinit var BLACK_ROOK: Texture
    lateinit var BLACK_BISHOP: Texture
    lateinit var BLACK_KNIGHT: Texture
    lateinit var BLACK_QUEEN: Texture
    lateinit var BLACK_KING: Texture

    fun load() {
        BLACK_TILE = Texture("$pathPrefix/black_tile.png")
        WHITE_TILE = Texture("$pathPrefix/white_tile.png")

        SELECTION_HIGHLIGHT = Texture("$pathPrefix/selection_highlight.png")
        ATTACKABLE_HIGHLIGHT = Texture("$pathPrefix/attackable_highlight.png")
        MOVABLE_HIGHLIGHT = Texture("$pathPrefix/movable_highlight.png")

        WHITE_PAWN = Texture("$pathPrefix/$piecesPathPrefix/white_pawn.png")
        WHITE_ROOK = Texture("$pathPrefix/$piecesPathPrefix/white_rook.png")
        WHITE_BISHOP = Texture("$pathPrefix/$piecesPathPrefix/white_bishop.png")
        WHITE_KNIGHT = Texture("$pathPrefix/$piecesPathPrefix/white_knight.png")
        WHITE_QUEEN = Texture("$pathPrefix/$piecesPathPrefix/white_queen.png")
        WHITE_KING = Texture("$pathPrefix/$piecesPathPrefix/white_king.png")

        BLACK_PAWN = Texture("$pathPrefix/$piecesPathPrefix/black_pawn.png")
        BLACK_ROOK = Texture("$pathPrefix/$piecesPathPrefix/black_rook.png")
        BLACK_BISHOP = Texture("$pathPrefix/$piecesPathPrefix/black_bishop.png")
        BLACK_KNIGHT = Texture("$pathPrefix/$piecesPathPrefix/black_knight.png")
        BLACK_QUEEN = Texture("$pathPrefix/$piecesPathPrefix/black_queen.png")
        BLACK_KING = Texture("$pathPrefix/$piecesPathPrefix/black_king.png")
    }
}