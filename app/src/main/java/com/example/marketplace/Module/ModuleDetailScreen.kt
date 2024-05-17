package com.example.marketplace.Module

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.marketplace.R
import com.example.marketplace.dataClasses.ModuleData
import com.example.marketplace.dataClasses.StepData

@Composable
fun ModuleDetailScreen(module: ModuleData?, onBackClick: () -> Unit) {
    module?.let {
        var currentStepIndex by remember { mutableStateOf(0) }
        val currentStep = it.steps[currentStepIndex]

        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFBEBEBE))
                .padding(16.dp)
            ){
                IconButton(onClick = onBackClick) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_back_arrow),
                        contentDescription = "Back",
                        modifier = Modifier.size(48.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "${module.id}. ${module.title}",
                    style = MaterialTheme.typography.h6
                )
            }


//            if (currentStep.question == null) {
//                TextStep(step = currentStep) {
//                    if (currentStepIndex < module.steps.size - 1) {
//                        currentStepIndex++
//                    }
//                }
//            } else {
//                QuizStep(step = currentStep) { isCorrect ->
//                    if (isCorrect && currentStepIndex < module.steps.size - 1) {
//                        currentStepIndex++
//                    }
//                }
//            }
        }
    }
}

@Composable
fun TextStep(step: StepData, onNextClick: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(step.content, style = MaterialTheme.typography.body1)
        Button(onClick = onNextClick) {
            Text("Дальше")
        }
    }
}

@Composable
fun QuizStep(step: StepData, onAnswer: (Boolean) -> Unit) {
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(step.question!!, style = MaterialTheme.typography.body1)
        step.options!!.forEachIndexed { index, option ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable { selectedOptionIndex = index }) {
                RadioButton(
                    selected = selectedOptionIndex == index,
                    onClick = { selectedOptionIndex = index }
                )
                Text(option, style = MaterialTheme.typography.body1)
            }
        }
        Button(onClick = {
            onAnswer(selectedOptionIndex == step.correctAnswerIndex)
        }) {
            Text("Проверить")
        }
    }
}
