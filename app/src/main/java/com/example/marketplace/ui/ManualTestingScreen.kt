package com.example.marketplace.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.marketplace.R
import com.example.marketplace.dataClasses.ModuleData
import com.example.marketplace.dataClasses.ModuleInformation

@Composable
fun ManualTestingScreen(
    selectedAction: String,
    onBackArrowClicked: () -> Unit,
    onInfoClicked: () -> Unit,
    onModulesClicked: () -> Unit,
    onNewsClicked: () -> Unit,
    onReviewsClicked: () -> Unit,
    onModuleClicked: (ModuleData) -> Unit,
    infoText: String,
    newsText: String,
    reviews: List<String>,
    newReview: TextFieldValue,
    onNewReviewChanged: (TextFieldValue) -> Unit,
    onSendReviewClicked: (String) -> Unit
) {
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

        ScrollableButtonsRow(
            buttons = listOf("Инфо", "Модули", "Новости", "Отзывы"),
            onItemClick = { action ->
                when (action) {
                    "Инфо" -> onInfoClicked()
                    "Модули" -> onModulesClicked()
                    "Новости" -> onNewsClicked()
                    "Отзывы" -> onReviewsClicked()
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedAction) {
            "Инфо" -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colors.onBackground.copy(alpha = 0.1f))
                        .padding(16.dp)
                ) {
                    Text(infoText)
                }
            }

            "Модули" -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colors.onBackground.copy(alpha = 0.1f))
                        .padding(16.dp)
                ) {
                    items(ModuleInformation().modules) { module ->
                        ModuleItem(module, onModuleClicked)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
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
                ReviewsSection(
                    reviews = reviews,
                    newReview = newReview,
                    onNewReviewChanged = onNewReviewChanged,
                    onSendReviewClicked = onSendReviewClicked
                )
            }
        }
    }
}

@Composable
fun ReviewsSection(
    reviews: List<String>,
    newReview: TextFieldValue,
    onNewReviewChanged: (TextFieldValue) -> Unit,
    onSendReviewClicked: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.onBackground.copy(alpha = 0.1f))
                .padding(16.dp)
        ) {
            items(reviews) { review ->
                Text(
                    text = review,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            BasicTextField(
                value = newReview,
                onValueChange = onNewReviewChanged,
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .border(1.dp, Color.Gray, RectangleShape)
                    .background(Color.White, shape = RectangleShape)
                    .padding(8.dp)
            )

            Button(
                onClick = { onSendReviewClicked(newReview.text) },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Отправить")
            }
        }
    }
}

@Composable
fun ModuleItem(module: ModuleData, onModuleClicked: (ModuleData) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.surface)
            .padding(16.dp)
            .clickable { onModuleClicked(module) }
    ) {
        Text(module.title, style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(4.dp))
        Text(module.description, style = MaterialTheme.typography.body2)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "Баллы: ${module.userProgressStep} / ${module.maxSteps}",
            style = MaterialTheme.typography.body2
        )
    }
}

@Composable
fun ScrollableButtonsRow(
    modifier: Modifier = Modifier,
    buttons: List<String>,
    onItemClick: (String) -> Unit
) {
    LazyRow(
        modifier = modifier
    ) {
        items(buttons) { text ->
            Button(
                onClick = { onItemClick(text) },
            ) {
                Text(text)
            }
        }
    }
}
