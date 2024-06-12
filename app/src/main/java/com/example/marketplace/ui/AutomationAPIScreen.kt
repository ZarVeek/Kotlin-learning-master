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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.marketplace.R
import com.example.marketplace.dataClasses.ModuleData
import com.example.marketplace.dataClasses.ModuleInformation
import com.example.marketplace.model.Review

@Composable
fun AutomationAPIScreen(
    selectedAction: String,
    onBackArrowClicked: () -> Unit,
    onInfoClicked: () -> Unit,
    onModulesClicked: () -> Unit,
    onNewsClicked: () -> Unit,
    onReviewsClicked: () -> Unit,
    onModuleClicked: (ModuleData) -> Unit,
    infoText: String,
    newsText: String,
    reviews: List<Review>,
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