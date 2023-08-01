package com.example.codenamecatfish

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.codenamecatfish.databinding.FragmentDifficultyScreenBinding

class DifficultyScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentDifficultyScreenBinding>(inflater, R.layout.fragment_difficulty_screen, container, false)

        val bundle = Bundle()

        binding.btnEasy.setOnClickListener{ view: View ->
            bundle.putSerializable("difficulty", Difficulty.EASY)
            view.findNavController().navigate(R.id.action_difficultyScreen_to_gameScreen, bundle)
        }

        binding.btnNormal.setOnClickListener{ view: View ->
            bundle.putSerializable("difficulty", Difficulty.NORMAL)
            view.findNavController().navigate(R.id.action_difficultyScreen_to_gameScreen, bundle)
        }

        binding.btnHard.setOnClickListener{ view: View ->
            bundle.putSerializable("difficulty", Difficulty.HARD)
            view.findNavController().navigate(R.id.action_difficultyScreen_to_gameScreen, bundle)
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}