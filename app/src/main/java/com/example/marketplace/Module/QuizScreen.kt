package com.example.marketplace.Module

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.marketplace.dataClasses.StepData
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.marketplace.R

@Composable
fun QuizScreen(step: StepData) {
    var selectedOption by remember { mutableStateOf("") }
    var isAnswerChecked by remember { mutableStateOf(false) }
    var isAnswerCorrect by remember { mutableStateOf(false) }

    val correctAnswer = step.correctAnswer

    val options: List<String> = step.options ?: emptyList()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text(text = step.content, style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.weight(1f, fill = false)
        ) {
            items(options) { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedOption = option }
                        .padding(vertical = 4.dp)
                ) {
                    RadioButton(
                        selected = selectedOption == option,
                        onClick = { selectedOption = option }
                    )
                    Text(text = option)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                isAnswerChecked = true
                isAnswerCorrect = selectedOption == correctAnswer
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Отправить")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isAnswerChecked) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                val icon = if (isAnswerCorrect) R.drawable.ic_check else R.drawable.ic_cross
                val message = if (isAnswerCorrect) "Абсолютно точно" else "Неправильно, попробуйте еще раз"
                val messageColor = if (isAnswerCorrect) Color.Green else Color.Red

                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = message,
                    color = messageColor,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}
