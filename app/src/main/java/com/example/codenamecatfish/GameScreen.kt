package com.example.codenamecatfish

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.codenamecatfish.databinding.FragmentGameScreenBinding
import kotlin.random.Random

class GameScreen : Fragment() {
    private var repeatSequenceChances = 3
    private var gameStarted = false
    private var currentIndexInSequence = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //INFLATE that pup
        val binding: FragmentGameScreenBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game_screen, container, false
        )
        var repeatsLeft = 3
        var gameStarting = false
        val sequence =
            generateGameSequence(GameScreenArgs.fromBundle(requireArguments()).difficulty)
        val enteredSequencePoints = mutableListOf<SequencePoint>()
        binding.btnRed.setOnClickListener { view: View ->
            enteredSequencePoints.add(SequencePoint.RED)
            checkGameState(sequence, enteredSequencePoints, view, binding)
        }
        binding.btnYellow.setOnClickListener { view: View ->
            enteredSequencePoints.add(SequencePoint.YELLOW)
            checkGameState(sequence, enteredSequencePoints, view, binding)
        }
        binding.btnBlue.setOnClickListener { view: View ->
            enteredSequencePoints.add(SequencePoint.BLUE)
            checkGameState(sequence, enteredSequencePoints, view, binding)
        }
        binding.btnGreen.setOnClickListener { view: View ->
            enteredSequencePoints.add(SequencePoint.GREEN)
            checkGameState(sequence, enteredSequencePoints, view, binding)
        }
        binding.btnRepeat.setOnClickListener {
            if (!gameStarting) {
                gameStarting = !gameStarting
                binding.btnRepeat.text = getString(R.string.repeat_sequence_3_left)
                showBlinkingAnimation(sequence.subList(0, 1), binding)
                currentIndexInSequence++
            } else {
                repeatsLeft--
                when (repeatsLeft) {
                    2 -> {
                        binding.btnRepeat.text = getString(R.string.two_repeats_left)
                        showBlinkingAnimation(sequence.subList(0, currentIndexInSequence), binding)
                    }

                    1 -> {
                        binding.btnRepeat.text = getString(R.string.one_repeats_left)
                        showBlinkingAnimation(sequence.subList(0, currentIndexInSequence), binding)
                    }

                    0 -> {
                        binding.btnRepeat.text = getString(R.string.zero_repeats_left)
                        showBlinkingAnimation(sequence.subList(0, currentIndexInSequence), binding)
                    }

                    else -> Toast.makeText(context, getString(R.string.no_repeats_left), Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }

    private fun checkGameState(
        sequence: List<SequencePoint>,
        enteredSequencePoints: MutableList<SequencePoint>,
        view: View,
        binding: FragmentGameScreenBinding
    ) {
        //if we haven't gotten all items from sequence yet - return
        if (enteredSequencePoints.size < currentIndexInSequence) return

        for ((index, value) in enteredSequencePoints.withIndex()) {
            //if next guess doesn't match master sequence
            if (value != sequence[index]) {
                Toast.makeText(context, "Wrong color chosen - game over! :(", Toast.LENGTH_LONG)
                    .show()
                view.findNavController()
                    .navigate(GameScreenDirections.actionGameScreenToTitleScreen())
            }

            //if next guess is the last in master sequence - you win and get a prize :D
            if (index >= sequence.size - 1) winTheGame(view)
            //otherwise, play animation for next section of master sequence
            else {
                if(index == enteredSequencePoints.size-1) showBlinkingAnimation(sequence.subList(0, ++currentIndexInSequence), binding)
            }
        }
        enteredSequencePoints.clear()
    }

    private fun winTheGame(view: View) {

        Toast.makeText(context, "WINNER!", Toast.LENGTH_LONG).show()
        view.findNavController()
            .navigate(GameScreenDirections.actionGameScreenToTitleScreen())
    }

    private fun showBlinkingAnimation(points: List<SequencePoint>, binding: FragmentGameScreenBinding) {
        var animator: ObjectAnimator
        val littleAni = mutableListOf<Animator>()
        for (p in points) {
                when (p) {
                    SequencePoint.RED -> {
                        animator = ObjectAnimator.ofArgb(
                            binding.btnRed,
                            "backgroundColor",
                            0xFF424242.toInt(),
                            0xFFFF0000.toInt()
                        )
                    }

                    SequencePoint.YELLOW -> {
                        animator = ObjectAnimator.ofArgb(
                            binding.btnYellow,
                            "backgroundColor",
                            0xFF424242.toInt(),
                            0xFFFFE500.toInt()
                        )
                    }

                    SequencePoint.BLUE -> {
                        animator = ObjectAnimator.ofArgb(
                            binding.btnBlue,
                            "backgroundColor",
                            0xFF424242.toInt(),
                            0xFF008EFF.toInt()
                        )
                    }

                    else -> {
                        animator = ObjectAnimator.ofArgb(
                            binding.btnGreen,
                            "backgroundColor",
                            0xFF424242.toInt(),
                            0xFF00FF00.toInt()
                        )
                    }
                }
                animator.duration = 250
                animator.repeatCount = 1
                animator.repeatMode = ObjectAnimator.REVERSE
//                animator.start()
            littleAni.add(animator)
            }
        val bigAni = AnimatorSet()
        bigAni.playSequentially(littleAni)
        bigAni.start()

    }

    private fun generateGameSequence(difficulty: Difficulty): List<SequencePoint> {
        return when (difficulty) {
            Difficulty.EASY -> {
                // Easy difficulty
                List(5) {
                    when (Random.nextInt(0, 4)) {
                        0 -> SequencePoint.RED
                        1 -> SequencePoint.YELLOW
                        2 -> SequencePoint.BLUE
                        else -> SequencePoint.GREEN
                    }
                }
            }

            Difficulty.NORMAL -> {
                // Normal difficulty
                List(10) {
                    when (Random.nextInt(0, 4)) {
                        0 -> SequencePoint.RED
                        1 -> SequencePoint.YELLOW
                        2 -> SequencePoint.BLUE
                        else -> SequencePoint.GREEN
                    }
                }
            }

            else -> {
                // Hard difficulty
                List(15) {
                    when (Random.nextInt(0, 4)) {
                        0 -> SequencePoint.RED
                        1 -> SequencePoint.YELLOW
                        2 -> SequencePoint.BLUE
                        else -> SequencePoint.GREEN
                    }
                }
            }
        }
    }


}