package com.aruna.covidtracker.tracking

data class StateModel(
    val loc: String,
    val discharged: Int,
    val deaths: Int,
    val totalConfirmed: Int,
    val todayDeaths: Int = 0,
    val todayCases: Int = 0,
)
