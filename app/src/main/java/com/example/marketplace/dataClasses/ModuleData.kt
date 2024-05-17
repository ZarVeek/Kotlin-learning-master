package com.example.marketplace.dataClasses

import java.io.Serializable

data class ModuleData(
    val id: Int,
    val title: String,
    val description: String,
    val maxSteps: Int,
    val userProgressStep: Int,
    val steps: List<StepData>
) : Serializable
