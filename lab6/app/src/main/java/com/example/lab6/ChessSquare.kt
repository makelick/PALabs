package com.example.lab6

enum class PieceType {
    FOX, HOUND, AVAILABLE_MOVE
}
class ChessSquare(val x: Int, val y: Int, var piece: PieceType? = null) {

    private fun isFox() = piece == PieceType.FOX

    private fun isHound() = piece == PieceType.HOUND

    fun getAvailableMoves(board : List<ChessSquare>): List<ChessSquare> {
        val moves = mutableListOf<ChessSquare>()
        if (isFox()) {
            moves.add(ChessSquare(x - 1, y - 1))
            moves.add(ChessSquare(x - 1, y + 1))
            moves.add(ChessSquare(x + 1, y - 1))
            moves.add(ChessSquare(x + 1, y + 1))
        } else if (isHound()) {
            moves.add(ChessSquare(x - 1, y - 1))
            moves.add(ChessSquare(x - 1, y + 1))
        }

        val availableMoves : MutableList<ChessSquare> = mutableListOf()
        for (move in moves) {
                if (move.x in 0..7 && move.y in 0..7 && board[move.x * 8 + move.y].piece == null) {
                    availableMoves.add(move)
                }
            }

        return availableMoves
    }
}