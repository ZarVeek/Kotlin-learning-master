package com.example.marketplace

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.marketplace.ui.AutomationAndroidScreen

class AutomationAndroidActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                val onBackArrowClicked = {
                    startActivity(Intent(this, MainActivity::class.java))
                }

                var selectedAction by remember { mutableStateOf("Инфо") }

                AutomationAndroidScreen(
                    selectedAction = selectedAction,
                    onBackArrowClicked = onBackArrowClicked,
                    onInfoClicked = { selectedAction = "Инфо" },
                    onModulesClicked = { selectedAction = "Модули" },
                    onNewsClicked = { selectedAction = "Новости" },
                    onReviewsClicked = { selectedAction = "Отзывы" },
                )
            }
        }
    }
}