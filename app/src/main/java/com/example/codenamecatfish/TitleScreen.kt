package com.example.codenamecatfish

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.codenamecatfish.databinding.FragmentTitleScreenBinding

class TitleScreen : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentTitleScreenBinding>(inflater, R.layout.fragment_title_screen, container, false)

//        binding.playButton.setOnClickListener { view: View ->
//            view.findNavController().navigate(R.id.where.ever.this.is.going)
//        }
        binding.rulesButton.setOnClickListener {view: View ->
            view.findNavController().navigate(R.id.action_titleScreen_to_rulesScreen)
        }

        binding.unlockedButton.setOnClickListener  { view: View ->
            view.findNavController().navigate(R.id.action_titleScreen_to_unlockablesScreen)
        }

        binding.statsButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_titleScreen_to_statsScreen)
        }

        binding.easterEgg.setOnClickListener {
            val yeltsakcir = Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/dQw4w9WgXcQ?si=3pdKDjcKseIQrayT"))
            startActivity(yeltsakcir)
        }

        return binding.root
    }

}