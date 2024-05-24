package com.example.marketplace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.marketplace.ui.FullScreenVideoScreen

class FullScreenVideoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val videoUri = intent.getStringExtra("video_uri")

        setContent {
            videoUri?.let { FullScreenVideoScreen(videoUri) }
        }
    }
}