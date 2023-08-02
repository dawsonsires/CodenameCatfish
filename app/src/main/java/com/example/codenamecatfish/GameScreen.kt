package com.example.codenamecatfish

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.codenamecatfish.databinding.FragmentGameScreenBinding
import kotlinx.coroutines.selects.select
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.FileWriter
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import kotlin.random.Random

class GameScreen : Fragment() {
    private lateinit var difficulty: Difficulty
    private var gameStarted = false
    private var currentIndexInSequence = 0
    private lateinit var data: SharedPreferences
    private lateinit var store: SharedPreferences.Editor
    private lateinit var sounds: List<Triple<String, String, Boolean>>
    private val redSound = R.raw.wat
    private val yellowSound =  R.raw.wat1
    private val blueSound = R.raw.wat2
    private val greenSound = R.raw.wat3
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        data = requireActivity().getPreferences(Context.MODE_PRIVATE)
        store = data.edit()

        //INFLATE that pup
        val binding: FragmentGameScreenBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game_screen, container, false
        )
        var repeatsLeft = 3
        difficulty = GameScreenArgs.fromBundle(requireArguments()).difficulty
        val sequence =
            generateGameSequence(difficulty)
        val enteredSequencePoints = mutableListOf<SequencePoint>()

        sounds = getSoundsArray()

        binding.btnRed.setOnClickListener { view: View ->
            animate(binding.btnRed, 0xFF6C0000.toInt(), 0xFFFF0000.toInt(), redSound).start()
            if(gameStarted) {
                enteredSequencePoints.add(SequencePoint.RED)
                checkGameState(sequence, enteredSequencePoints, view, binding)
            }
        }
        binding.btnYellow.setOnClickListener { view: View ->
            animate(binding.btnYellow, 0xFF918200.toInt(), 0xFFFFE500.toInt(), yellowSound).start()
            if(gameStarted) {
                enteredSequencePoints.add(SequencePoint.YELLOW)
                checkGameState(sequence, enteredSequencePoints, view, binding)
            }
        }
        binding.btnBlue.setOnClickListener { view: View ->
            animate(binding.btnBlue, 0xFF014881.toInt(), 0xFF008EFF.toInt(), blueSound).start()
            if(gameStarted) {
                enteredSequencePoints.add(SequencePoint.BLUE)
                checkGameState(sequence, enteredSequencePoints, view, binding)
            }
        }
        binding.btnGreen.setOnClickListener { view: View ->
            animate(binding.btnGreen, 0xFF005100.toInt(), 0xFF00FF00.toInt(), greenSound).start()
            if(gameStarted) {
                enteredSequencePoints.add(SequencePoint.GREEN)
                checkGameState(sequence, enteredSequencePoints, view, binding)
            }
        }
        binding.btnRepeat.setOnClickListener {
            if (!gameStarted) {
                gameStarted = true
                binding.btnRepeat.text = "Repeat Sequence ($repeatsLeft left)"
                showBlinkingAnimation(sequence.subList(0, 1), binding)
                currentIndexInSequence++
                store.putInt("timesPlayed", data.getInt("timesPlayed", 0) + 1)
                store.commit()

            } else {
                repeatsLeft--
                if (repeatsLeft >= 0) {
                        binding.btnRepeat.text = "Repeat Sequence ($repeatsLeft left)"
                        showBlinkingAnimation(sequence.subList(0, currentIndexInSequence), binding)
                } else
                    Toast.makeText(context, getString(R.string.no_repeats_left), Toast.LENGTH_SHORT).show()
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
                if(data.getInt("highScore", 0) < currentIndexInSequence) {
                    store.putInt("highScore", currentIndexInSequence)
                    store.commit()
                }
                view.findNavController()
                    .navigate(R.id.action_gameScreen_to_titleScreen)
                return
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

    private fun getSoundsArray(): List<Triple<String, String, Boolean>> {
        val reader = BufferedReader(
            InputStreamReader(
                context?.assets?.open("sounds.csv")
            )
        )

        val unlockedSounds = data.getStringSet("unlocked", mutableSetOf<String>())

        val sounds = mutableListOf<Triple<String, String, Boolean>>()
        reader.forEachLine {
            val (title, file) = it.split(',')
            sounds.add(Triple(title.trim(), file.trim(), unlockedSounds.toString().contains(title.trim())))
        }
        return sounds.toList()
    }

    private fun pickRandomSound(): String{
        var usedSounds = mutableListOf<Int>()
        val filteredSounds = sounds.filter { it.third }
        var random: Int
        do {
            random = (filteredSounds.indices).random()
        } while (usedSounds.contains(random))
        usedSounds.add(random)
        return filteredSounds[random].second
    }

    fun unlockSounds(n: Int){
        val filteredSounds = sounds.filter { !it.third }
        val num = n.coerceAtMost(filteredSounds.size)
        var chosen = mutableListOf<Int>()
        var unlockedSounds = data.getStringSet("unlocked", mutableSetOf<String>())

        var i = 0
        while(i < num){
            val selected = (0..filteredSounds.size).random()
            if(chosen.contains(selected))
                continue
            i++
            chosen.add(selected)
            unlockedSounds?.add(filteredSounds[selected].first)
            Toast.makeText(context, "Unlocked sound: ${filteredSounds[selected].first}", Toast.LENGTH_SHORT).show()
        }
        store.putStringSet("unlocked", unlockedSounds)
        store.commit()
    }

    private fun winTheGame(view: View) {
        val bundle = Bundle()
        Toast.makeText(context, "WINNER!", Toast.LENGTH_LONG).show()
        when (difficulty) {
            Difficulty.EASY -> bundle.putSerializable("difficulty", Difficulty.EASY)
            Difficulty.NORMAL -> bundle.putSerializable("difficulty", Difficulty.NORMAL)
            else -> bundle.putSerializable("difficulty", Difficulty.HARD)
        }
        view.findNavController()
            .navigate(R.id.action_gameScreen_to_titleScreen)
            unlockSounds(currentIndexInSequence/5)
        if(data.getInt("highScore", 0) < currentIndexInSequence) {
            store.putInt("highScore", currentIndexInSequence)
            store.commit()
        }
    }

    private fun animate(button: Button, color1: Int, color2: Int, sound: Int): ObjectAnimator{

        val animator = ObjectAnimator.ofArgb(
            button,
            "backgroundColor",
            color1,
            color2
        )
        val mp = MediaPlayer.create(context, sound)
        animator.duration = 250
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.doOnStart { mp.start() }
        animator.doOnEnd { mp.release() }   // remove used player, otherwise it eventually stops working
        return animator
    }
    private fun showBlinkingAnimation(points: List<SequencePoint>, binding: FragmentGameScreenBinding) {
        val littleAni = mutableListOf<Animator>()
        for (p in points) {
            littleAni.add(when (p) {
                SequencePoint.RED -> animate(binding.btnRed, 0xFF6C0000.toInt(), 0xFFFF0000.toInt(), redSound)

                SequencePoint.YELLOW -> animate(binding.btnYellow, 0xFF918200.toInt(), 0xFFFFE500.toInt(), yellowSound)

                SequencePoint.BLUE -> animate(binding.btnBlue, 0xFF014881.toInt(), 0xFF008EFF.toInt(), blueSound)

                else -> animate(binding.btnGreen, 0xFF005100.toInt(), 0xFF00FF00.toInt(), greenSound)
            })
        }
        val bigAni = AnimatorSet()
        bigAni.startDelay = 1000
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