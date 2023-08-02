package com.example.codenamecatfish

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.codenamecatfish.databinding.FragmentUnlockablesScreenBinding
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

private lateinit var data: SharedPreferences
private lateinit var sounds: List<Pair<String, String>>

class UnlockablesScreen : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentUnlockablesScreenBinding>(inflater, R.layout.fragment_unlockables_screen, container, false)

        data = requireActivity().getPreferences(Context.MODE_PRIVATE)
        sounds = getSoundsArray()

        binding.unlockedTitle.setOnClickListener{
            showAll(binding)
        }

        createButtons(binding)

        return binding.root
    }

    private fun getSoundsArray(): List<Pair<String, String>> {
        val reader = BufferedReader(
            InputStreamReader(
                context?.assets?.open("sounds.csv")
            )
        )

        val unlockedSounds = data.getStringSet("unlocked", mutableSetOf<String>())

        val sounds = mutableListOf<Pair<String, String>>()
        reader.forEachLine {
            val (title, file) = it.split(',')
            if (unlockedSounds.toString().contains(title.trim()))
                sounds.add(Pair(title.trim(), file.trim()))
        }
        return sounds.toList()
    }

    private fun createButtons(binding: FragmentUnlockablesScreenBinding){
        if(sounds.isEmpty()) {
            val text = TextView(context)
            text.text = "No sounds unlocked! :(\nPlay the game and come back later!"
            text.gravity = Gravity.CENTER
            binding.buttonPanel.addView(text)
        }

        for (i in sounds.indices) {
            val button = Button(context)
            button.setText(sounds[i].first)
            val mp = MediaPlayer()
            val afd = context?.assets!!.openFd("sounds/${sounds[i].second}")
            mp.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            mp.prepare()
            button.text = sounds[i].first
            button.gravity = Gravity.CENTER
            button.setOnClickListener{
                mp.start()
            }

            binding.buttonPanel.addView(button)
        }
    }

    fun showAll(binding: FragmentUnlockablesScreenBinding) {
        val reader = BufferedReader(
            InputStreamReader(
                context?.assets?.open("sounds.csv")
            )
        )

        val sounds = mutableListOf<Pair<String, String>>()
        reader.forEachLine {
            val (title, file) = it.split(',')
            sounds.add(title.trim() to file.trim())
        }

        binding.buttonPanel.removeAllViews()

        for (i in sounds.indices) {
            val button = Button(context)
            button.setText(sounds[i].first)
            val mp = MediaPlayer()
            val afd = context?.assets!!.openFd("sounds/${sounds[i].second}")
            mp.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            mp.prepare()
            button.text = sounds[i].first
            button.gravity = Gravity.CENTER
            button.setOnClickListener{
                mp.start()
            }
            binding.buttonPanel.addView(button)
        }
    }

}
