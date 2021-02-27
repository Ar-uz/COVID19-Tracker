package com.aruna.covidtracker.survey

import androidx.lifecycle.ViewModel

const val SAFE = 100
const val UNSAFE = 101

class SurveyViewModel: ViewModel() {

    val questions = arrayListOf(
        QuestionModel(
            0,
            "Are you experiencing any of the following symptoms?",
            arrayListOf("cough", "fever", "Difficulty in breathing", "loss of senses of smell and taste", "none of the above")
        ),
        QuestionModel(
            1,
            "Have you ever had any of the following?",
            arrayListOf("Diabetes", "Hypertension", "Lung disease", "Heart disease", "Kidney disorder", "None of the above")
        ),
        QuestionModel(
            2,
            "Have you traveled anywhere internationally in the last 28-45 days?",
            arrayListOf("Yes", "No")
        ),
        QuestionModel(
            3,
            "Which of the following apply to you?",
            arrayListOf("I have recently interacted or lived with someone who has tested positive for COVID-19", "I am a healthcare worker and I examined a COVID-19 confirmed case without protective gear", "None of the above")
        ),
        QuestionModel(
            4,
            "Which of the following apply to you?",
            arrayListOf("Traveled internationally in the last 14 days", "Recently interacted or lived or currently live with someone who has tested positive for COVID-19", "I am a healthcare worker", "None of the above")
        ),
        QuestionModel(
            5,
            "When did the interaction take place?",
            arrayListOf("Less than 5 days ago", "Greater than 5 days ago", "Greater than 14 days ago", "None of the above")
        ),
    )

    private var currentIndex = -1

    fun getQuestion(index: Int): QuestionModel {
        currentIndex = index
        return questions[index]
    }

    fun getResponse(): Int {
        return when(currentIndex) {
            questions[0].id -> return questions[1].id
            questions[1].id -> {
                return if(questions[0].userOption != 4) {
                    questions[4].id
                } else {
                    questions[2].id
                }
            }
            questions[2].id -> {
                return if(questions[2].userOption == 0) {
                    UNSAFE
                } else {
                    questions[3].id
                }
            }
            questions[3].id -> {
                return if(questions[3].userOption == 2) {
                    SAFE
                } else {
                    questions[5].id
                }
            }
            else -> UNSAFE
        }
    }

}