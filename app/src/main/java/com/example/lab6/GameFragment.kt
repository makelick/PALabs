package com.example.lab6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lab6.databinding.FragmentGameBinding


class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val difficulty = arguments?.getInt("difficulty")

        val recyclerView = binding.boardRecyclerview
        val squares = createSquares()
        val adapter = ChessBoardAdapter(squares, requireContext())
        recyclerView.adapter = adapter

        val gridLayoutManager = GridLayoutManager(requireContext(), 8)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL

        recyclerView.layoutManager = gridLayoutManager

    }

    private fun createSquares(): List<ChessSquare> {
        val squares = mutableListOf<ChessSquare>()
        for (x in 0 until 8) {
            for (y in 0 until 8) {
                squares.add(ChessSquare(x, y))
            }
        }
        return squares
    }

}