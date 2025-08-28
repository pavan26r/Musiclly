package com.example.musicuiapp.face

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.Call

interface FaceApiService {
    @Multipart
    @POST("facepp/v3/detect")
    fun detectEmotion(
        @Part("api_key") apiKey: RequestBody,
        @Part("api_secret") apiSecret: RequestBody,
        @Part imageFile: MultipartBody.Part,
        @Part("return_attributes") returnAttributes: RequestBody
    ): Call<FaceApiResponse>
}