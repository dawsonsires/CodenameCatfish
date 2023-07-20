package com.example.codenamecatfish

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.codenamecatfish.databinding.FragmentTitleScreenBinding

class TitleScreen : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentTitleScreenBinding>(inflater, R.layout.fragment_title_screen, container, false)
//        binding.playButton.setOnClickListener { view: View ->
//            view.findNavController().navigate(R.id.where.ever.this.is.going)
//        }
        return binding.root
    }

}