package com.example.lab6

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.lab6.databinding.FragmentDifficultyChooseBinding

class DifficultyChooseFragment : Fragment() {

    private lateinit var binding: FragmentDifficultyChooseBinding
    private var difficulty: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDifficultyChooseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.easyButton.setOnClickListener {
            difficulty = 0
            navigateToGameFragment()
        }
        binding.normalButton.setOnClickListener {
            difficulty = 1
            navigateToGameFragment()
        }
        binding.hardButton.setOnClickListener {
            difficulty = 2
            navigateToGameFragment()
        }
    }

    private fun navigateToGameFragment() {

        val action = DifficultyChooseFragmentDirections.difficultyToGame(difficulty)
        binding.root.findNavController().navigate(action)
    }

}