package com.example.marketplace.Module

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import com.example.marketplace.dataClasses.ModuleData

class ModuleDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                val module = intent.getSerializableExtra("module") as? ModuleData
                ModuleDetailScreen(module = module) {
                    finish()
                }
            }
        }
    }
}