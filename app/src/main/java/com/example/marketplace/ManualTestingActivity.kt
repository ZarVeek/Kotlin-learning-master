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
import com.example.marketplace.Module.ModuleDetailActivity
import com.example.marketplace.dataClasses.ModuleData
import com.example.marketplace.ui.ManualTestingScreen

class ManualTestingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                var selectedAction by remember { mutableStateOf("Инфо") }

                val onBackArrowClicked = {
                    startActivity(Intent(this, MainActivity::class.java))
                }

                val onModuleClicked: (ModuleData) -> Unit = { module ->
                    // Обработка нажатия на модуль
                    val intent = Intent(this, ModuleDetailActivity::class.java)
                    intent.putExtra("module", module)
                    startActivity(intent)
                }

                ManualTestingScreen(
                    selectedAction = selectedAction,
                    onBackArrowClicked = onBackArrowClicked,
                    onInfoClicked = { selectedAction = "Инфо" },
                    onModulesClicked = { selectedAction = "Модули" },
                    onNewsClicked = { selectedAction = "Новости" },
                    onReviewsClicked = { selectedAction = "Отзывы" },
                    onModuleClicked = onModuleClicked
                )
            }
        }
    }
}