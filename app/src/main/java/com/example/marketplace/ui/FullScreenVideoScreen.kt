package com.example.marketplace.ui

import android.widget.VideoView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import java.io.File

@Composable
fun FullScreenVideoScreen(videoName: String) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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