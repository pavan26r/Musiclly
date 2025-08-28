package com.example.musicuiapp.face

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

data class FaceApiResponse(
    val faces: List<Face>
)
data class Face(
    val attributes: Attributes
)
data class Attributes(val emotion: Emotion)

data class Emotion(
    val happiness: Double,
    val sadness: Double,
    val anger: Double
)
