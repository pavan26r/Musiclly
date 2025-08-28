package com.example.musicuiapp

import CameraPreview
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.musicuiapp.face.EmotionScreen
import com.example.musicuiapp.ui.theme.MusicUIAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MusicUIAppTheme {
           Screen()
            }
        }
    }
}