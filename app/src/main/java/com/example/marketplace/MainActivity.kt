package com.example.marketplace

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.example.marketplace.ui.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isUserLoggedIn()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setContent {
            MaterialTheme {
                MainScreen(
                    onLogout = {
                        logoutUser()
                    },
                    onManualTestingClicked = {
                        startActivity(Intent(this, ManualTestingActivity::class.java))
                    },
                    onFrontEndAutomationClicked = {
                        startActivity(Intent(this, AutomationFrontEndActivity::class.java))
                    },
                    onAPIAutomationClicked = {
                        startActivity(Intent(this, AutomationAPIActivity::class.java))
                    },
                    onAndroidAutomationClicked = {
                        startActivity(Intent(this, AutomationAndroidActivity::class.java))
                    }
                )
            }
        }
    }

    private fun isUserLoggedIn(): Boolean {
        val sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("is_user_logged_in", false)
    }

    private fun logoutUser() {
        // Очистка данных о состоянии входа пользователя
        val sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("is_user_logged_in", false)
            apply()
        }

        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
