package com.example.marketplace.Module

import android.content.Context
import android.content.Intent
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
import android.widget.VideoView
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.viewinterop.AndroidView
import com.example.marketplace.FullScreenVideoActivity
import com.example.marketplace.api.CompileRequest
import com.example.marketplace.api.CompileResponse
import com.example.marketplace.api.CompilerApiService
import com.example.marketplace.api.RetrofitInstance
import java.io.File
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ModuleDetailScreen(module: ModuleData?, onBackClick: () -> Unit) {
    module?.let {
        var currentStepIndex by remember { mutableStateOf(0) }
        val context = LocalContext.current
        var codeInput by remember { mutableStateOf("") }
        var codeOutput by remember { mutableStateOf("") }

        Column(modifier = Modifier.fillMaxSize()) {
            TopMenuBar(module.title, onBackClick)

            StepNavigationRow(
                steps = it.steps,
                currentStepIndex = currentStepIndex,
                onStepClick = { index -> currentStepIndex = index }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                val step = it.steps[currentStepIndex]
                when {
                    step.question == "1" -> QuizScreen(step)
                    step.content.endsWith(".pdf") -> PdfViewer(context, step.content, 3f, currentStepIndex)
                    step.video == "1" -> VideoPlayer(context, step.content)
                    step.code == "1" -> CodeCompilerScreen(step)
                    else -> Text(step.content)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            NavigationButtons(
                onNextClick = {
                    if (currentStepIndex < module.steps.size - 1) {
                        currentStepIndex += 1
                    }
                }
            )
        }
    }
}

@Composable
fun TopMenuBar(title: String, onBackClick: () -> Unit) {
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
            text = title,
            style = MaterialTheme.typography.h6
        )
    }
}

@Composable
fun StepNavigationRow(steps: List<StepData>, currentStepIndex: Int, onStepClick: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        steps.forEachIndexed { index, step ->
            val isStepSelected = index == currentStepIndex
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .clickable { onStepClick(index) },
                text = step.id.toString(),
                style = MaterialTheme.typography.body2.copy(
                    fontWeight = if (isStepSelected) FontWeight.Bold else FontWeight.Normal
                )
            )
        }
    }
}

@Composable
fun NavigationButtons(onNextClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Button(onClick = onNextClick) {
            Text("Дальше")
        }
    }
}

@Composable
fun PdfViewer(context: Context, fileName: String, scaleFactor: Float, currentStepIndex: Int) {
    var pdfRenderer by remember(currentStepIndex) { mutableStateOf(loadPdfFromAssets(context, fileName)) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        pdfRenderer?.let { renderer ->
            val pageBitmaps = remember(currentStepIndex) { mutableStateListOf<Bitmap>() }
            for (i in 0 until renderer.pageCount) {
                val page = renderer.openPage(i)
                val bitmap = Bitmap.createBitmap(
                    (page.width * scaleFactor).toInt(),
                    (page.height * scaleFactor).toInt(),
                    Bitmap.Config.ARGB_8888
                )
                val matrix = Matrix().apply { setScale(scaleFactor, scaleFactor) }
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

@Composable
fun VideoPlayer(context: Context, videoName: String) {
    val videoView = remember { VideoView(context) }
    var isPlaying by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(factory = {
            videoView.apply {
                val assetFileDescriptor = context.assets.openFd(videoName)
                val inputStream = assetFileDescriptor.createInputStream()
                val tempFile = File.createTempFile("video", null, context.cacheDir)
                val outputStream = tempFile.outputStream()
                inputStream.copyTo(outputStream)
                inputStream.close()
                outputStream.close()
                setVideoPath(tempFile.absolutePath)
                setOnPreparedListener { mp ->
                    mp.setOnVideoSizeChangedListener { _, _, _ ->
                        mp.start()
                        isPlaying = true
                    }
                }
                setOnCompletionListener {
                    isPlaying = false
                }
            }
        }, modifier = Modifier.fillMaxWidth())

        if (!isPlaying) {
            Button(
                onClick = {
                    videoView.start()
                    isPlaying = true
                },
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
                    .alpha(0f) // Make the button invisible
            ) {
                Text("Начать воспроизведение")
            }
        } else {
            Button(
                onClick = {
                    videoView.pause()
                    isPlaying = false
                },
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
                    .alpha(0f) // Make the button invisible
            ) {
                Text("Пауза")
            }
        }

        Button(
            onClick = {
                val intent = Intent(context, FullScreenVideoActivity::class.java)
                intent.putExtra("video_name", videoName)
                context.startActivity(intent)
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text("Полноэкранный режим")
        }
    }
}

@Composable
fun CodeCompilerScreen(step: StepData) {
    var codeInput by remember { mutableStateOf("@file:JvmName(\"JDoodle\")\n" +
            "fun main() {\n" +
            "  val x: Int = 10\n" +
            "  val y: Int = 25\n" +
            "  val z = x + y\n" +
            "  println(\"The sum of \$x + \$y is  \$z\")\n" +
            "}") }
    var codeOutput by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = step.content, style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))

        BasicTextField(
            value = codeInput,
            onValueChange = { codeInput = it },
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { compileCode(codeInput) { result -> codeOutput = result } }) {
            Text("Выполнить код")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = codeOutput, style = MaterialTheme.typography.body1)
    }
}

fun compileCode(code: String, onResult: (String) -> Unit) {
    val retrofitService = RetrofitInstance.getJDoodleRetrofitInstance().create(CompilerApiService::class.java)
    val call = retrofitService.executeCode(
        CompileRequest(
            script = code,
            clientId = "b12e39882fadad6de88f43f7e4d9db04",
            clientSecret = "94ca8cae70bfb1548d493861c3582bfb4399930b5575914bc4627340640d54cd"
        )
    )

    call.enqueue(object : Callback<CompileResponse> {
        override fun onResponse(call: Call<CompileResponse>, response: Response<CompileResponse>) {
            if (response.isSuccessful) {
                response.body()?.let {
                    onResult(it.output)
                }
            } else {
                onResult("Ошибка выполнения кода")
            }
        }

        override fun onFailure(call: Call<CompileResponse>, t: Throwable) {
            onResult("Ошибка выполнения кода: ${t.message}")
        }
    })
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
        PdfRenderer(parcelFileDescriptor)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}