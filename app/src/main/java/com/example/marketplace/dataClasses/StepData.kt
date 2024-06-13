package com.example.marketplace.dataClasses

import java.io.Serializable

data class StepData(
    val id: Int,
    val content: String,
    val question: String? = null,
    val options: List<String>? = null,
    val correctAnswer: String? = null,
    val code: String? = null,
    val video: String? = null,
    val correctAnswerIndex: Int? = null
) : Serializable
