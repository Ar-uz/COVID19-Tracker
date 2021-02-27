package com.aruna.covidtracker.tracking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.aruna.covidtracker.databinding.FragmentAreaWiseTrackingBinding
import org.json.JSONArray
import org.json.JSONObject

class AreaWiseTrackingFragment : Fragment() {

    private var _binding: FragmentAreaWiseTrackingBinding? = null
    private val binding get() = _binding

    private val adapter = TrackingAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAreaWiseTrackingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.rvTracking?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rvTracking?.adapter = adapter
        binding?.slTracking?.setOnRefreshListener {
            binding?.slTracking?.isRefreshing = false
        }
        getHistoryData()
    }

    private fun getHistoryData() {
        binding?.slTracking?.isRefreshing = true
        binding?.rvTracking?.visibility = View.INVISIBLE

        val stateHistoryData = hashMapOf<String, ArrayList<StateModel>>()

        val queue = Volley.newRequestQueue(requireContext())
        val stringRequest = object: StringRequest(
            Method.GET,
            "https://disease.sh/v3/covid-19/nyt/states?lastdays=200",
            Response.Listener{
                val data = JSONArray(it)
                for(i in 0 until data.length()) {
                    val state = JSONObject(data[i].toString())
                    val stateModel = StateModel(
                        state.getString("state"),
                        state.getInt("cases") - state.getInt("deaths"),
                        state.getInt("deaths"),
                        state.getInt("cases")
                    )
                    if(stateHistoryData[stateModel.loc] != null) {
                        stateHistoryData[stateModel.loc]?.add(stateModel)
                    } else {
                        stateHistoryData[stateModel.loc] = arrayListOf(stateModel)
                    }
                }
                getStateWiseData(stateHistoryData)
            },
            Response.ErrorListener {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                binding?.slTracking?.isRefreshing = false
                binding?.rvTracking?.visibility = View.VISIBLE
            }
        ) {}
        queue.add(stringRequest)
    }

    private fun getStateWiseData(stateHistoryData: HashMap<String, ArrayList<StateModel>>) {
        binding?.slTracking?.isRefreshing = true
        binding?.rvTracking?.visibility = View.INVISIBLE
        val queue = Volley.newRequestQueue(requireContext())
        val stringRequest = object: StringRequest(
            Method.GET,
            "https://disease.sh/v3/covid-19/states?sort=active&yesterday=false&allowNull=false",
            Response.Listener {
                val regional = JSONArray(it)
                val states = ArrayList<StateModel>()
                for(i in 0 until regional.length()) {
                    val state = JSONObject(regional[i].toString())
                    val stateModel = StateModel(
                        state.getString("state"),
                        state.getInt("recovered"),
                        state.getInt("deaths"),
                        state.getInt("cases"),
                        state.getInt("todayDeaths"),
                        state.getInt("todayCases"),
                    )
                    if(stateHistoryData[stateModel.loc] != null) {
                        states.add(stateModel)
                    }
                }
                adapter.states = states
                adapter.history = stateHistoryData
                binding?.slTracking?.isRefreshing = false
                binding?.rvTracking?.visibility = View.VISIBLE
            },
            Response.ErrorListener {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                binding?.slTracking?.isRefreshing = false
                binding?.rvTracking?.visibility = View.VISIBLE
            }
        ) {}
        queue.add(stringRequest)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}