package com.example.codenamecatfish

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.codenamecatfish.databinding.FragmentTitleScreenBinding
import java.lang.Error

class TitleScreen : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentTitleScreenBinding>(inflater, R.layout.fragment_title_screen, container, false)

        binding.rulesButton.setOnClickListener {view: View ->
            view.findNavController().navigate(R.id.action_titleScreen_to_rulesScreen)
        }

        binding.unlockedButton.setOnClickListener  { view: View ->
            view.findNavController().navigate(R.id.action_titleScreen_to_unlockablesScreen)
        }

        binding.statsButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_titleScreen_to_statsScreen)
        }

        binding.playButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_titleScreen_to_difficultyScreen)
        }

        binding.easterEgg.setOnClickListener {

            Toast.makeText(context, "Hi", Toast.LENGTH_SHORT)
//            try {
//                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:///dQw4w9WgXcQ?si=3pdKDjcKseIQrayT")))
//            } catch (e: Error){
//                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/dQw4w9WgXcQ?si=3pdKDjcKseIQrayT")))   // if YouTube is not installed
//            }
        }

        val growShrinkAnimation = AnimationUtils.loadAnimation(context, R.anim.grow_shrink )
        binding.splashText.startAnimation(growShrinkAnimation)

        return binding.root
    }
}

//https://developer.android.com/training/data-storage