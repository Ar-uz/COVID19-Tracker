package com.aruna.covidtracker.main

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.aruna.covidtracker.R
import com.aruna.covidtracker.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.cvTracking?.setOnClickListener {
            binding?.root?.let { view -> Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_areaWiseTrackingFragment) }
        }
        binding?.cvSurvey?.setOnClickListener {
            binding?.root?.let { view -> Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_surveyFragment) }
        }
        binding?.cvInfo?.setOnClickListener {
            binding?.root?.let { view -> Navigation.findNavController(view).navigate(R.id.action_dashboardFragment_to_infoFragment) }
        }
        binding?.tvDashboard?.text = "Hello ${requireActivity().getSharedPreferences("user", MODE_PRIVATE).getString("name", null)}"
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}