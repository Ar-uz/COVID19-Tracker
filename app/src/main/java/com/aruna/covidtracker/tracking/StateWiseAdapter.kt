package com.aruna.covidtracker.tracking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aruna.covidtracker.databinding.LayoutStateRowBinding

class StateRowViewHolder(private val binding: LayoutStateRowBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(stateModel: StateModel) {
        binding.stateModel = stateModel
    }
}

class StateWiseAdapter: RecyclerView.Adapter<StateRowViewHolder>() {
    var states = mutableListOf<StateModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateRowViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutStateRowBinding.inflate(layoutInflater, parent, false)
        return StateRowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StateRowViewHolder, position: Int) {
        holder.bind(states[position])
    }

    override fun getItemCount(): Int {
        return states.size
    }
}