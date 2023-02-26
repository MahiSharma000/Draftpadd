package com.example.draftpad.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = ""

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {

    //post to /api/v1/login the username and password as form objects
    @GET("login")
    suspend fun login(): List<User>

    // logout the user by passing id
    @GET("logout")
    suspend fun logout(): List<User>

    //get user by id /api/v1/user/{id}
    @GET("user/{id}")
    suspend fun getUser(): List<User>

    // get profile by id /api/v1/profile/{id}
    @GET("profile/{id}")
    suspend fun getProfile(): List<User>

    // get all categories /api/v1/categories
    @GET("categories")
    suspend fun getCategories(): List<Category>

    @GET("register")
    abstract fun createUser(user: User): Any?




}

object ApiClient {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}