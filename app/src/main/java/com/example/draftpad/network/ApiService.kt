package com.example.draftpad.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

private const val BASE_URL = "http://192.168.238.25:5000/api/v1/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {

    @POST("login")
    suspend fun login(@Body user: User): Any

    @GET("logout")
    suspend fun logout(): List<User>

    @GET("user/{id}")
    suspend fun getUser(): List<User>

    @GET("profile/{id}")
    suspend fun getProfile(): List<User>

    @GET("categories")
    suspend fun getCategories(): List<Category>

    @Headers("Content-Type: application/json")
    @POST("register")
    suspend fun register(@Body user: User): Any
}

object ApiClient {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}