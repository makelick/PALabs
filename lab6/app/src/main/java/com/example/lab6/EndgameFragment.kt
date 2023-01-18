package com.example.lab6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.lab6.databinding.FragmentEndgameBinding


class EndgameFragment : Fragment() {

    private lateinit var binding: FragmentEndgameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEndgameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isWon = arguments?.getBoolean("isWon")
        val difficulty = arguments?.getInt("difficulty")!!.toInt()

        if (isWon == true) {
            binding.endgameTitle.text = getString(R.string.win_title)
        } else {
            binding.endgameTitle.text = getString(R.string.lose_title)
        }

        binding.toStartButton.setOnClickListener {
            val action = EndgameFragmentDirections.endgameToDifficulty()
            binding.root.findNavController().navigate(action)
        }
        binding.toGameButton.setOnClickListener {
            val action = EndgameFragmentDirections.endgameToGame(difficulty)
            binding.root.findNavController().navigate(action)
        }
    }
}