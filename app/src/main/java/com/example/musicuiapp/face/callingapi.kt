package com.example.musicuiapp.face

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

fun detectEmotionFromFile(file: File, onResult: (Emotion?) -> Unit) {
    val apiKey = RequestBody.create("text/plain".toMediaTypeOrNull(), "YOUR_API_KEY")
    val apiSecret = RequestBody.create("text/plain".toMediaTypeOrNull(), "YOUR_API_SECRET")
    val returnAttributes = RequestBody.create("text/plain".toMediaTypeOrNull(), "emotion")

    val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
    val body = MultipartBody.Part.createFormData("image_file", file.name, requestFile)

    val call = RetrofitClient.instance.detectEmotion(apiKey, apiSecret, body, returnAttributes)
    call.enqueue(object : retrofit2.Callback<FaceApiResponse> {
        override fun onResponse(call: retrofit2.Call<FaceApiResponse>, response: retrofit2.Response<FaceApiResponse>) {
            if (response.isSuccessful) {
                val emotion = response.body()?.faces?.firstOrNull()?.attributes?.emotion
                onResult(emotion)
            } else {
                onResult(null)
            }
        }

        override fun onFailure(call: retrofit2.Call<FaceApiResponse>, t: Throwable) {
            onResult(null)
        }
    })
}
