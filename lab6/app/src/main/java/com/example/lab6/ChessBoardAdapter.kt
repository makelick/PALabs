package com.example.lab6

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class ChessBoardAdapter(private val squares: List<ChessSquare>, private val difficulty : Int)
    : RecyclerView.Adapter<ChessBoardAdapter.ViewHolder>() {

    val viewHolders : MutableList<ViewHolder> = mutableListOf()
    private var activePiecePos : Int = 0
    private val activeMovesPos : MutableList<ChessSquare> = mutableListOf()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.chess_square_image)
        val pieceButton: ImageButton = itemView.findViewById(R.id.piece_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chess_square_view, parent, false)
        val vh = ViewHolder(view)
        viewHolders.add(vh)
        return vh
    }

    override fun getItemCount(): Int {
        return squares.size
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {

        if (position == 63) {
            foxMove(holder)
        }
        val square = squares[position]
        holder.imageView.setImageResource(getImageResource(square.x, square.y))

        if (square.piece == PieceType.FOX) {
            holder.pieceButton.setImageResource(R.drawable.fox)
            holder.pieceButton.visibility = View.VISIBLE
        }
        else if (square.piece == PieceType.HOUND) {
            holder.pieceButton.setImageResource(R.drawable.dog)
            holder.pieceButton.visibility = View.VISIBLE
        }

        holder.pieceButton.setOnClickListener {

            when (square.piece) {
                PieceType.AVAILABLE_MOVE -> {
                    clearActiveMoves()
                    squares[activePiecePos].piece = null
                    squares[position].piece = PieceType.HOUND

                    holder.pieceButton.setImageResource(R.drawable.dog)
                    holder.pieceButton.visibility = View.VISIBLE
                    viewHolders[activePiecePos].pieceButton.visibility = View.GONE

                    if (!isHoundWon()) foxMove(holder)
                    else navigateToEndgameFragment(holder.imageView, true)
                }

                PieceType.HOUND -> {
                    clearActiveMoves()

                    activePiecePos = position
                    val moves = square.getAvailableMoves(squares)
                    activeMovesPos.addAll(moves)

                    for (move in moves) {
                        squares[move.x * 8 + move.y].piece = PieceType.AVAILABLE_MOVE
                        viewHolders[move.x * 8 + move.y].pieceButton.setImageResource(R.drawable.point)
                        viewHolders[move.x * 8 + move.y].pieceButton.visibility = View.VISIBLE
                    }
                }
                else -> {
                    clearActiveMoves()
                    activePiecePos = 0
                }
            }
        }
    }

    fun foxMove(holder: ViewHolder) {
        val fox = squares.find { it.piece == PieceType.FOX }!!
        val moves = fox.getAvailableMoves(squares)
        var move = moves.random()

        for (i in 0..5) {
            move = moves.random()
            if (fox.x < move.x) break
        }

        squares[fox.x * 8 + fox.y].piece = null
        squares[move.x * 8 + move.y].piece = PieceType.FOX

        viewHolders[fox.x * 8 + fox.y].pieceButton.visibility = View.GONE
        viewHolders[move.x * 8 + move.y].pieceButton.setImageResource(R.drawable.fox)
        viewHolders[move.x * 8 + move.y].pieceButton.visibility = View.VISIBLE

        if (isFoxWon()) {
            navigateToEndgameFragment(holder.imageView, false)
        }
    }

    private fun navigateToEndgameFragment(holder: View, isWon : Boolean, ) {
        val action = GameFragmentDirections.gameToEndgame(isWon, difficulty)
        holder.findNavController().navigate(action)
    }

    private fun isFoxWon() : Boolean {
        for (square in squares) {
            if (square.piece == PieceType.FOX) {
                return square.x == 7
            }
        }
        return false
    }

    private fun isHoundWon() : Boolean {
        for (square in squares) {
            if (square.piece == PieceType.FOX) {
                return square.getAvailableMoves(squares).isEmpty()
            }
        }
        return true
    }

    private fun clearActiveMoves() {
        activeMovesPos.forEach {
            viewHolders[it.x * 8 + it.y].pieceButton.visibility = View.GONE
            squares[it.x * 8 + it.y].piece = null
        }
        activeMovesPos.clear()
    }
    private fun getImageResource(x: Int, y: Int): Int {
        return if ((x + y) % 2 == 0) {
            R.drawable.white_square
        } else {
            R.drawable.brown_square
        }
    }
}
