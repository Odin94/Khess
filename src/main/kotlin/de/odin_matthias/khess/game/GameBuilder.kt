package de.odin_matthias.khess.game

import de.odin_matthias.khess.board.BoardBuilder


class GameBuilder() {
    val board = BoardBuilder()
        .generateTiles()
        .build()
}