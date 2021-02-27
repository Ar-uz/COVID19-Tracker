package com.aruna.covidtracker.survey

data class QuestionModel(
    val id: Int,
    val question: String,
    val options: ArrayList<String>,
    var userOption: Int = -1,
)