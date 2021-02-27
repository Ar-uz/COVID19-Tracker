package com.aruna.covidtracker.survey

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aruna.covidtracker.databinding.LayoutResponseRowBinding

class ResponseRowViewHolder(private val binding: LayoutResponseRowBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(questionId: Int, optionText: String, position: Int, selectedOption: Int, responseItemListener: ResponseItemListener) {
        binding.tvResponse.text = optionText
        if(position == selectedOption) {
            binding.tvResponse.setBackgroundColor(Color.parseColor("#FFBB86FC"))
        } else {
            binding.tvResponse.setBackgroundColor(Color.parseColor("#FFFFFFFF"))
        }
        binding.tvResponse.setOnClickListener {
            responseItemListener.onItemClick(questionId, position)
        }
        if(selectedOption != -1) {
            binding.tvResponse.isEnabled = false
        }
    }
}

interface ResponseItemListener {
    fun onItemClick(position: Int, selectedOption: Int)
}

class ResponsesAdapter(private val questionId: Int, private val responseItemListener: ResponseItemListener): RecyclerView.Adapter<ResponseRowViewHolder>() {

    var options = arrayListOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var selectedOption = -1
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResponseRowViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutResponseRowBinding.inflate(layoutInflater, parent, false)
        return ResponseRowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResponseRowViewHolder, position: Int) {
        holder.bind(questionId, options[position], position, selectedOption, responseItemListener)
    }

    override fun getItemCount(): Int {
        return options.size
    }
}