package com.example.musicuiapp.face
import CameraPreview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.io.File

@Composable
fun EmotionScreen(viewModel: EmotionViewModel = viewModel()) {
    val emotion by viewModel.emotion

    var capturedFile by remember { mutableStateOf<File?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreview(onImageCaptured = { file ->
            capturedFile = file
            viewModel.detectEmotion(file)
        })
        if (emotion != null) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Text("Detected Emotion: $emotion", fontSize = 20.sp)
                Text("🎵 Suggested songs: ${getSongsForEmotion(emotion!!).joinToString()}")
            }
        }
    }
}
fun getSongsForEmotion(emotion: String): List<String> {
    return when (emotion) {
        "happiness" -> listOf("Happy Song 1", "Happy Song 2")
        "sadness" -> listOf("Sad Song 1", "Sad Song 2")
        "anger" -> listOf("Rock Song 1", "Metal Song 2")
        else -> listOf("Chill Song 1", "Calm Song 2")
    }
}
