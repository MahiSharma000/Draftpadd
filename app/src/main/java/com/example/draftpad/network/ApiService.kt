package com.example.draftpad.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

private const val BASE_URL = "http://192.168.18.91:5000/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @GET("user/{id}")
    suspend fun getUser(): List<User>

    @GET("profile/{id}")
    suspend fun getProfile(): List<User>

    // post user form data to api/v1/register and return register response
    @FormUrlEncoded
    @POST("api/v1/register")
    suspend fun register(
        @Field("username") userName: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("last_seen") lastSeen: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("api/v1/login")
    suspend fun login(
        @Field("username") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("api/v1/logout")
    suspend fun logout(): LogoutResponse

    // get all categories
    @GET("api/v1/categories")
    suspend fun getCategories(): CategoryAllResponse

    @GET("api/v1/categories/{id}")
    fun getCategoryBooks(id: Int): BooksByCategoryResponse
}

object ApiClient {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}