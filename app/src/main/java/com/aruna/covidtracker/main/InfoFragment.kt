package com.aruna.covidtracker.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aruna.covidtracker.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}