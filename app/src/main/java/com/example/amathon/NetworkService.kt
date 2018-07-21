package com.example.amathon

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface NetworkService {

    @Multipart
    @POST("upload")
    fun postImage(
            @Part image: MultipartBody.Part?
    ) : Call<Result>
}