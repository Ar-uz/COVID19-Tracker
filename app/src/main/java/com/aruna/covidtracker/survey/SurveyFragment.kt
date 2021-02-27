package com.aruna.covidtracker.survey

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.aruna.covidtracker.R
import com.aruna.covidtracker.databinding.FragmentSurveyBinding
import com.aruna.covidtracker.databinding.LayoutSurveyResultDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SurveyFragment : Fragment(), SurveyItemListener {

    private var _binding: FragmentSurveyBinding? = null
    private val binding get() = _binding

    private val surveyViewModel by viewModels<SurveyViewModel>()

    private lateinit var adapter: SurveyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSurveyBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.rvSurvey?.layoutManager = LinearLayoutManager(requireContext())
        adapter = SurveyAdapter(this)
        adapter.questions.add(surveyViewModel.getQuestion(0))
        binding?.rvSurvey?.adapter = adapter
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onItemClick(position: Int, selectedOption: Int) {
        surveyViewModel.questions[position].userOption = selectedOption
        when(val response = surveyViewModel.getResponse()) {
            SAFE -> {
                val dialogBinding = LayoutSurveyResultDialogBinding.inflate(layoutInflater, null, false)
                dialogBinding.ivSurveyResult.setImageResource(R.drawable.ic_safe)
                val dialog = MaterialAlertDialogBuilder(requireContext())
                    .setCancelable(false)
                    .setTitle("Result")
                    .setView(dialogBinding.root)
                    .setMessage("You have low risk of being infected. However, you are advised to take necessary precautions and follow the SOP.")
                    .setPositiveButton("Confirm") { dialog, _ ->
                        dialog.dismiss()
                        binding?.root?.let { Navigation.findNavController(it).popBackStack() }
                    }
                dialog.show()
            }
            UNSAFE -> {
                val dialogBinding = LayoutSurveyResultDialogBinding.inflate(layoutInflater, null, false)
                dialogBinding.ivSurveyResult.setImageResource(R.drawable.ic_unsafe)
                val dialog = MaterialAlertDialogBuilder(requireContext())
                    .setCancelable(false)
                    .setTitle("Result")
                    .setView(dialogBinding.root)
                    .setMessage("You have risk of being infected. You are advised to take necessary precautions and get a checkup as soon as possible.")
                    .setPositiveButton("Confirm") { dialog, _ ->
                        dialog.dismiss()
                        binding?.root?.let { Navigation.findNavController(it).popBackStack() }
                    }
                dialog.show()
            }
            else -> {
                adapter.questions.add(surveyViewModel.getQuestion(response))
            }
        }
    }

}