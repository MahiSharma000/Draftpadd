package com.example.draftpad.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

private const val BASE_URL = "http://192.168.125.124:5000/"

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


    @FormUrlEncoded
    @POST("api/v1/register")
    suspend fun register(
        @Field("username") userName: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("last_seen") lastSeen: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("api/v1/profile")
    suspend fun createProfile(
        @Field("user_id") user_id: String,
        @Field("first_name") first_name: String,
        @Field("last_name") last_name: String,
        @Field("about") about: String,
        @Field("profile_pic") profile_pic: String,
        @Field("book_written") book_written: String,
        @Field("followers") followers: Int,
        @Field("following") following: Int,
        @Field("created_at") created_at: String,
        @Field("updated_at") updated_at: String,
        @Field("books_read") bookRead: String,
        @Field("dob") dob: String,
        @Field("phone") phone: String,

        ): UserDataResponse
    @FormUrlEncoded
    @POST("api/v1/book")
    suspend fun createBook(
        @Field("title") title: String,
        @Field("description") description: String,
        @Field("category_id") category_id: Int,
        @Field("user_id") user_id: Int,
        @Field("status") status: String
    ): CreateBookResponse

    @FormUrlEncoded
    @POST("api/v1/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("api/v1/logout")
    suspend fun logout(): LogoutResponse

    @GET("api/v1/categories")
    suspend fun getCategories(): CategoryAllResponse

    @GET("api/v1/categories/{id}")
    fun getCategoryBooks(id: Int): BooksByCategoryResponse

    @GET("api/v1/books")
    suspend fun getBooks(): BooksAllResponse
}

object ApiClient {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}