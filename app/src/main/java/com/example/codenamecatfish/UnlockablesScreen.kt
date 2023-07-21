package com.example.codenamecatfish

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.codenamecatfish.databinding.FragmentUnlockablesScreenBinding

class UnlockablesScreen : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentUnlockablesScreenBinding>(inflater, R.layout.fragment_unlockables_screen, container, false)

        // TODO: PLAN FOR BUTTONS -> https://stackoverflow.com/questions/15642104/array-of-buttons-in-android

        return binding.root
    }
}