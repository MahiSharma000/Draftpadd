package com.example.draftpad.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

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

    @Multipart
    @POST("api/v1/profile")
    suspend fun createProfile(
        @Part("user_id") user_id: String,
        @Part("first_name") first_name: String,
        @Part("last_name") last_name: String,
        @Part("about") about: String,
        @Part("book_written") book_written: Int = 0,
        @Part("followers") followers: Int = 0,
        @Part("following") following: Int = 0,
        @Part("books_read") bookRead: Int = 0,
        @Part("dob") dob: String,
        @Part("phone") phone: String,
        @Part profile_pic: MultipartBody.Part?
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

    @GET("api/v1/category/{id}")
    suspend fun getCategoryBooks(@Path("id") id: Int): BooksByCategoryResponse

    @GET("api/v1/book/{id}")
    suspend fun getBook(@Path("id") id: Int): SelectedBookResponse

    @GET("api/v1/books")
    suspend fun getBooks(): BooksAllResponse

    @GET("api/v1/comments/{id}")
    suspend fun getComments(@Path("id") id: Int): CommentsResponse

    @GET("api/v1/chapters/{id}")
    suspend fun getChapters(@Path("id") id: Int): ChaptersResponse
    //get a chapter by chapter id
    @GET("api/v1/chapter/{id}")
    suspend fun getChapter(@Path("id") id: Int): ChapterResponse

}

object ApiClient {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}