package com.example.codenamecatfish

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.codenamecatfish.databinding.FragmentStatsScreenBinding

private lateinit var data: SharedPreferences
class StatsScreen : Fragment() {

    lateinit var binding: FragmentStatsScreenBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentStatsScreenBinding>(
            inflater,
            R.layout.fragment_stats_screen,
            container,
            false
        )
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> binding.chart.setImageResource(R.drawable.dark_chart)
            else -> Toast.makeText(context, "NOOOO", Toast.LENGTH_SHORT).show()
        }

        data = requireActivity().getPreferences(Context.MODE_PRIVATE)

        loadData()

        return binding.root
    }

    fun loadData(){
        binding.playCount.text = data.getInt("timesPlayed", 0).toString()
        binding.highScore.text = data.getInt("highScore", 0).toString()
        binding.soundsUnlocked.text = data.getStringSet("unlocked", setOf<String>())?.size.toString()
    }
}
