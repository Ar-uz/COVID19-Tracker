package com.aruna.covidtracker.survey

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.aruna.covidtracker.databinding.LayoutSurveyRowBinding

class SurveyRowViewHolder(private val binding: LayoutSurveyRowBinding, private val surveyItemListener: SurveyItemListener): RecyclerView.ViewHolder(binding.root), ResponseItemListener {
    private lateinit var adapter: ResponsesAdapter

    fun bind(questionModel: QuestionModel, position: Int) {
        binding.apply {
            question = questionModel
        }
        binding.rvResponses.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        adapter = ResponsesAdapter(position, this)
        binding.rvResponses.adapter = adapter
        adapter.options = questionModel.options
        adapter.selectedOption = questionModel.userOption

    }

    override fun onItemClick(position: Int, selectedOption: Int) {
        adapter.selectedOption = selectedOption
        surveyItemListener.onItemClick(position, selectedOption)
    }
}

interface SurveyItemListener {
    fun onItemClick(position: Int, selectedOption: Int)
}

class SurveyAdapter(private val surveyItemListener: SurveyItemListener): RecyclerView.Adapter<SurveyRowViewHolder>() {

    var questions = mutableListOf<QuestionModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurveyRowViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutSurveyRowBinding.inflate(layoutInflater, parent, false)
        return SurveyRowViewHolder(binding, surveyItemListener)
    }

    override fun onBindViewHolder(holder: SurveyRowViewHolder, position: Int) {
        holder.bind(questions[position], position)
    }

    override fun getItemCount(): Int {
        return questions.size
    }

}