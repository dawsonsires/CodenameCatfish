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

        binding.btnEasy.setOnClickListener{ view: View ->
            view.findNavController().navigate(R.id.action_difficultyScreen_to_gameScreen)
        }

        binding.btnNormal.setOnClickListener{ view: View ->
            view.findNavController().navigate(R.id.action_difficultyScreen_to_gameScreen)
        }

        binding.btnHard.setOnClickListener{ view: View ->
            view.findNavController().navigate(R.id.action_difficultyScreen_to_gameScreen)
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}