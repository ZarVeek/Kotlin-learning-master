package com.example.marketplace

import android.os.Bundle
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import java.io.File

class FullScreenVideoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val videoName = intent.getStringExtra("video_name")

        setContent {
            MaterialTheme {
                if (videoName != null) {
                    val context = this
                    AndroidView(factory = {
                        VideoView(context).apply {
                            val assetFileDescriptor = context.assets.openFd(videoName)
                            val inputStream = assetFileDescriptor.createInputStream()
                            val tempFile = File.createTempFile("video", null, context.cacheDir)
                            val outputStream = tempFile.outputStream()
                            inputStream.copyTo(outputStream)
                            inputStream.close()
                            outputStream.close()
                            setVideoPath(tempFile.absolutePath)
                            start()
                        }
                    }, modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}