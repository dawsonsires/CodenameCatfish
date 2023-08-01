package com.example.codenamecatfish

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.codenamecatfish.databinding.FragmentGameScreenBinding
import kotlin.random.Random

class GameScreen: Fragment() {

    private var collinSequenceIndex = 0
    private var repeatSequenceChances = 3

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //INFLATE that pup
        val binding: FragmentGameScreenBinding = DataBindingUtil.inflate<FragmentGameScreenBinding>(inflater,
            R.layout.fragment_game_screen, container, false)

        val sequence = generateGameSequence(GameScreenArgs.fromBundle(requireArguments()).difficulty)
        showSequenceAnimation(sequence.subList(0, collinSequenceIndex++), binding)



        return binding.root
    }

    private fun showSequenceAnimation(sequence: List<Int>, binding: FragmentGameScreenBinding) {
        var index = 0
        for (i in sequence) {
            Thread.sleep(1000)
            when (i) {
                0 -> {
                    binding.btnRed.setBackgroundColor(Color.parseColor(R.color.red.toString()))
                    Thread.sleep(500)
                    binding.btnRed.setBackgroundColor((Color.parseColor(R.color.buttonDefaultBackground.toString())))
                }
                1 -> {
                    binding.btnYellow.setBackgroundColor(Color.parseColor(R.color.yellow.toString()))
                    Thread.sleep(500)
                    binding.btnYellow.setBackgroundColor((Color.parseColor(R.color.buttonDefaultBackground.toString())))
                }
                2 -> {
                    binding.btnBlue.setBackgroundColor(Color.parseColor(R.color.blue.toString()))
                    Thread.sleep(500)
                    binding.btnBlue.setBackgroundColor((Color.parseColor(R.color.buttonDefaultBackground.toString())))
                }
                else -> {
                    binding.btnGreen.setBackgroundColor(Color.parseColor(R.color.green.toString()))
                    Thread.sleep(500)
                    binding.btnGreen.setBackgroundColor((Color.parseColor(R.color.buttonDefaultBackground.toString())))
                }
            }
            Thread.sleep(1000)
        }
    }

    private fun generateGameSequence(difficulty: Difficulty): List<Int> {
        return when (difficulty) {
            Difficulty.EASY -> {
                // Easy difficulty
                List(5) { Random.nextInt(0, 4) }
            }

            Difficulty.NORMAL -> {
                // Normal difficulty
                List(10) { Random.nextInt(0, 4) }
            }

            else -> {
                // Hard difficulty
                List(15) { Random.nextInt(0,4) }
            }
        }
    }
}