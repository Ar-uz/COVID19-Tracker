package com.aruna.covidtracker.tracking

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aruna.covidtracker.databinding.LayoutTrackOneBinding
import com.aruna.covidtracker.databinding.LayoutTrackZeroBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class TrackingSummaryViewHolder(private val binding: LayoutTrackZeroBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(history: HashMap<String, ArrayList<StateModel>>, states: List<StateModel>) {
        val statesLocs = ArrayList<String>()
        for(state in states) statesLocs.add(state.loc)
        binding.tvState.adapter = ArrayAdapter(binding.root.context, android.R.layout.simple_list_item_1, statesLocs)
        binding.tvState.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val stateHistory = history[states[position].loc] as ArrayList<StateModel>
                binding.tvActive.text = (states[position].totalConfirmed.minus((states[position].discharged)).minus((states[position].deaths))).toString()
                binding.tvConfirmed.text = states[position].totalConfirmed.toString()
                binding.tvDeaths.text = states[position].deaths.toString()
                binding.tvDischarged.text = states[position].discharged.toString()
                binding.tvLastDayConfirmed.text = states[position].todayCases.toString()
                binding.tvLastDayDeaths.text = states[position].todayDeaths.toString()

                val confirmed = arrayListOf<Entry>()
                val discharged = arrayListOf<Entry>()
                val deaths = arrayListOf<Entry>()

                for(i in 0 until stateHistory.size) {
                    confirmed.add(Entry(i.toFloat(), stateHistory[i].totalConfirmed.toFloat()))
                    discharged.add(Entry(i.toFloat(), stateHistory[i].discharged.toFloat()))
                    deaths.add(Entry(i.toFloat(), stateHistory[i].deaths.toFloat()))
                }

                val confirmedDataSet = LineDataSet(confirmed, "Confirmed")
                val dischargedDataSet = LineDataSet(discharged, "Recovered")
                val deathDataSet = LineDataSet(deaths, "Deaths")
                confirmedDataSet.setDrawCircles(false)
                confirmedDataSet.setDrawCircleHole(false)
                confirmedDataSet.lineWidth = 3f
                confirmedDataSet.color = Color.parseColor("#FF3D00")
                dischargedDataSet.setDrawCircles(false)
                dischargedDataSet.setDrawCircleHole(false)
                dischargedDataSet.lineWidth = 3f
                dischargedDataSet.color = Color.parseColor("#00C853")
                deathDataSet.setDrawCircles(false)
                deathDataSet.setDrawCircleHole(false)
                deathDataSet.lineWidth = 3f
                deathDataSet.color = Color.parseColor("#000000")
                binding.chart.data = LineData(confirmedDataSet)
                binding.chart.invalidate()
                setSelected(0)
                binding.tvConfirmedChart.setOnClickListener {
                    setSelected(0)
                    binding.chart.data = LineData(confirmedDataSet)
                    binding.chart.invalidate()
                }
                binding.tvDischargedChart.setOnClickListener {
                    setSelected(1)
                    binding.chart.data = LineData(dischargedDataSet)
                    binding.chart.invalidate()
                }
                binding.tvDeathsChart.setOnClickListener {
                    setSelected(2)
                    binding.chart.data = LineData(deathDataSet)
                    binding.chart.invalidate()
                }
            }

            private fun setSelected(index: Int) {
                val texts = arrayOf(binding.tvConfirmedChart, binding.tvDischargedChart, binding.tvDeathsChart)
                for(i in texts.indices) {
                    texts[i].isSelected = i == index
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }
    }
}

class TrackingStateWiseViewHolder(private val binding: LayoutTrackOneBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(states: MutableList<StateModel>) {
        binding.rvStates.layoutManager = LinearLayoutManager(binding.root.context)
        val adapter = StateWiseAdapter()
        adapter.states = states
        binding.rvStates.adapter = adapter
    }
}

class TrackingAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var states = mutableListOf<StateModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var history = hashMapOf<String, ArrayList<StateModel>>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            0 -> {
                val binding = LayoutTrackZeroBinding.inflate(layoutInflater, parent, false)
                TrackingSummaryViewHolder(binding)
            }
            else -> {
                val binding = LayoutTrackOneBinding.inflate(layoutInflater, parent, false)
                TrackingStateWiseViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is TrackingSummaryViewHolder -> {
                holder.bind(history, states)
            }
            is TrackingStateWiseViewHolder -> {
                holder.bind(states)
            }
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}