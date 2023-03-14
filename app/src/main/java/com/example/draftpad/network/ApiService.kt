package com.example.draftpad.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "http://192.168.1.41:5000/"

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
        @Field("book_written") book_written: Int = 0,
        @Field("followers") followers: Int = 0,
        @Field("following") following: Int = 0,
        @Field("books_read") bookRead: Int = 0,
        @Field("is_premium") is_premium: Boolean = false,
        @Field("dob") dob: String,
        @Field("phone") phone: String,
        @Field("profile_pic") profile_pic: String,
    ): UserDataResponse

    @FormUrlEncoded
    @POST("api/v1/book")
    suspend fun createBook(
        @Field("title") title: String,
        @Field("cover") cover: String,
        @Field("lang") lang: String,
        @Field("description") description: String,
        @Field("category_id") category_id: Int,
        @Field("user_id") user_id: Int,
        @Field("status") status: Int,
        @Field("created_at") created_at: String,
        @Field("views") views: Int = 0
    ): PostBookResponse

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
    suspend fun getComments(@Path("id") id: Int): GetCommentsResponse

    @FormUrlEncoded
    @POST("api/v1/comment")
    suspend fun addComment(
        @Field("content") content: String,
        @Field("user_id") user_id: Int,
        @Field("chapter_id") chapter_id: Int,
        createdAt: String,
        updatedAt: String
    ): PostCommentResponse

    @GET("api/v1/chapters/{id}")
    suspend fun getChapters(@Path("id") id: Int): ChaptersResponse
    //get a chapter by chapter id
    @GET("api/v1/chapter/{id}")
    suspend fun getChapter(@Path("id") id: Int): ChapterResponse

    @GET("api/v1/get_profile/{id}")
    suspend fun getProfile(@Path("id") id: Int): AuthorResponse

}

object ApiClient {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}