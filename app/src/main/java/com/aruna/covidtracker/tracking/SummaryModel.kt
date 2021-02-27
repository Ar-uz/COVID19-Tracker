package com.aruna.covidtracker.tracking

data class SummaryModel(
    var total: Int,
    var confirmedCasesIndian: Int,
    var confirmedCasesForeign: Int,
    var discharged: Int,
    var deaths: Int,
    var confirmedButLocationUnidentified: Int
)