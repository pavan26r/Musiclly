package com.example.musicuiapp.face

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class EmotionViewModel : ViewModel() {

    private val _emotion = androidx.compose.runtime.mutableStateOf<String?>(null)
    val emotion: androidx.compose.runtime.State<String?> = _emotion

    fun detectEmotion(file: File) {
        viewModelScope.launch {
            try {
                val apiKey = RequestBody.create("text/plain".toMediaTypeOrNull(), "_0dd4lc7wDBdmAzpIJYzcy_da28h3n6r")
                val apiSecret = RequestBody.create("text/plain".toMediaTypeOrNull(), "3vYcSo9NNtNKf0kveI1kf4cIbAHGFYDq")
                val returnAttributes =
                    RequestBody.create("text/plain".toMediaTypeOrNull(), "emotion")

                val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
                val body = MultipartBody.Part.createFormData("image_file", file.name, requestFile)

                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.instance
                        .detectEmotion(apiKey, apiSecret, body, returnAttributes)
                        .execute()
                }

                if (response.isSuccessful) {
                    val face = response.body()?.faces?.firstOrNull()
                    val emotion = face?.attributes?.emotion

                    val emotionMap = mapOf(
                        "happiness" to (emotion?.happiness ?: 0.0),
                        "sadness" to (emotion?.sadness ?: 0.0),
                        "anger" to (emotion?.anger ?: 0.0),
                    )

                    val topEmotion = emotionMap.maxByOrNull { it.value }?.key

                    _emotion.value = topEmotion ?: "Unknown"

                } else {
                    _emotion.value = "Error"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _emotion.value = "Exception: ${e.message}"
            }
        }
    }
}
