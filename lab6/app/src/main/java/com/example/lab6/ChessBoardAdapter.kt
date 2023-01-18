package com.example.lab6

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChessBoardAdapter(private val squares: List<ChessSquare>, private val context: Context)
    : RecyclerView.Adapter<ChessBoardAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.chess_square_image)
        val textView: TextView = itemView.findViewById(R.id.coordinates)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chess_square_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return squares.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val square = squares[position]
        holder.textView.text = context.getString(R.string.coordinates, square.x, square.y)
        holder.imageView.setImageResource(getImageResource(square.x, square.y))
    }

    private fun getImageResource(x: Int, y: Int): Int {
        return if ((x + y) % 2 == 0) {
            R.drawable.white_square
        } else {
            R.drawable.brown_square
        }
    }
}
