package com.example.marketplace.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.marketplace.R

@Composable
fun AutomationAPIScreen(
    selectedAction: String,
    onBackArrowClicked: () -> Unit,
    onInfoClicked: () -> Unit,
    onModulesClicked: () -> Unit,
    onNewsClicked: () -> Unit,
    onReviewsClicked: () -> Unit
) {

    var courseInfoText = """
        Курс "Автоматизации API"
        
        Привет! Этот курс предназначен для тех, кто хочет научиться основам ручного тестирования 
        и развить свои навыки в этой области. Мы рассмотрим основные принципы тестирования,
        методы и техники, а также лучшие практики при выполнении тестовых заданий.
        
        Надеемся, что этот курс поможет вам стать успешным тестировщиком!
    """.trimIndent()

    var modulesText = "Список модулей курса"
    var newsText = "Новости об обновлении курса"
    var reviewsText = "Отзывы о курсе"

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .clickable { onBackArrowClicked() }
                    .background(MaterialTheme.colors.primary.copy(alpha = 0.2f)),
                contentAlignment = Alignment.TopStart
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = "Back",
                    modifier = Modifier.size(45.dp)
                )
            }

            Image(
                painter = painterResource(id = R.drawable.manual_testing),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            Button(
                onClick = onInfoClicked
            ) {
                Text("Инфо")
            }

            Button(
                onClick = onModulesClicked
            ) {
                Text("Модули")
            }

            Button(
                onClick = onNewsClicked
            ) {
                Text("Новости")
            }

            Button(
                onClick = onReviewsClicked
            ) {
                Text("Отзывы")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedAction) {
            "Инфо" -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colors.onBackground.copy(alpha = 0.1f))
                        .padding(16.dp)
                ) {
                    Text(courseInfoText)
                }
            }

            "Модули" -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colors.onBackground.copy(alpha = 0.1f))
                        .padding(16.dp)
                ) {
                    Text(modulesText)
                }
            }

            "Новости" -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colors.onBackground.copy(alpha = 0.1f))
                        .padding(16.dp)
                ) {
                    Text(newsText)
                }
            }

            "Отзывы" -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colors.onBackground.copy(alpha = 0.1f))
                        .padding(16.dp)
                ) {
                    Text(reviewsText)
                }
            }
        }
    }
}
