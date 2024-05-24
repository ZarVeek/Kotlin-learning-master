package com.example.marketplace.ui

import android.net.Uri
import android.widget.VideoView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun FullScreenVideoScreen(videoUri: String) {
    val context = LocalContext.current
    val uri = Uri.parse(videoUri)
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AndroidView(factory = {
            VideoView(context).apply {
                setVideoURI(uri)
                start()
            }
        }, modifier = Modifier.fillMaxSize())
    }
}