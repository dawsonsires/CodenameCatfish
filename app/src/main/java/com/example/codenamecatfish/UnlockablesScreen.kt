package com.example.codenamecatfish

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.example.codenamecatfish.databinding.FragmentUnlockablesScreenBinding
import java.io.File

class UnlockablesScreen : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentUnlockablesScreenBinding>(inflater, R.layout.fragment_unlockables_screen, container, false)

        return binding.root
    }
}