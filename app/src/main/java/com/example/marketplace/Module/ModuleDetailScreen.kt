package com.example.marketplace.Module

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.marketplace.R
import com.example.marketplace.dataClasses.ModuleData
import com.example.marketplace.dataClasses.StepData
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import java.io.File

@Composable
fun ModuleDetailScreen(module: ModuleData?, onBackClick: () -> Unit) {
    module?.let {
        var currentStepIndex by remember { mutableStateOf(0) }
        val context = LocalContext.current

        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFFBEBEBE))
                    .padding(16.dp)
            ) {
                IconButton(onClick = onBackClick) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_back_arrow),
                        contentDescription = "Back",
                        modifier = Modifier.size(48.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "${module.id}. ${module.title}",
                    style = MaterialTheme.typography.h6
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                it.steps.forEachIndexed { index, step ->
                    val isStepSelected = index == currentStepIndex
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                            .clickable { currentStepIndex = index },
                        text = step.id.toString(),
                        style = MaterialTheme.typography.body2.copy(
                            fontWeight = if (isStepSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                val content = it.steps[currentStepIndex].content
                if (content.endsWith(".pdf")) {
                    PdfViewer(context, content, 1.5f)
                } else {
                    Text(content)
                }
            }

            Spacer(modifier = Modifier.width(16.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        if (currentStepIndex < module.steps.size - 1) {
                            currentStepIndex += 1
                        }
                    }
                ) {
                    Text("Дальше")
                }
            }
        }
    }
}

@Composable
fun TextStep(step: StepData, onNextClick: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(step.content, style = MaterialTheme.typography.body1)
        Button(onClick = onNextClick) {
            Text("Дальше")
        }
    }
}

@Composable
fun QuizStep(step: StepData, onAnswer: (Boolean) -> Unit) {
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(step.question!!, style = MaterialTheme.typography.body1)
        step.options!!.forEachIndexed { index, option ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable { selectedOptionIndex = index }) {
                RadioButton(
                    selected = selectedOptionIndex == index,
                    onClick = { selectedOptionIndex = index }
                )
                Text(option, style = MaterialTheme.typography.body1)
            }
        }
        Button(onClick = {
            onAnswer(selectedOptionIndex == step.correctAnswerIndex)
        }) {
            Text("Проверить")
        }
    }
}

@Composable
fun PdfViewer(context: Context, fileName: String, scaleFactor: Float) {
    val pdfRenderer = remember { loadPdfFromAssets(context, fileName) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        pdfRenderer?.let { renderer ->
            val pageBitmaps = remember { mutableStateListOf<Bitmap>() }
            for (i in 0 until renderer.pageCount) {
                val page = renderer.openPage(i)
                val bitmap = Bitmap.createBitmap(
                    (page.width * scaleFactor).toInt(),
                    (page.height * scaleFactor).toInt(),
                    Bitmap.Config.ARGB_8888
                )
                val matrix = Matrix().apply {
                    setScale(scaleFactor, scaleFactor)
                }
                page.render(bitmap, null, matrix, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                pageBitmaps.add(bitmap)
                page.close()
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                for (bitmap in pageBitmaps) {
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "PDF Page",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        } ?: run {
            Text(text = "PDF file not found", color = Color.Red, style = MaterialTheme.typography.h6)
        }
    }
}

private fun loadPdfFromAssets(context: Context, fileName: String): PdfRenderer? {
    return try {
        val assetManager = context.assets
        val file = File(context.cacheDir, fileName)
        assetManager.open(fileName).use { inputStream ->
            file.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }

        val parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
        val pdfRenderer = PdfRenderer(parcelFileDescriptor)

        return pdfRenderer
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}