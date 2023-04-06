package com.example.draftpad.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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

    @FormUrlEncoded
    @POST("api/v1/register")
    suspend fun register(
        @Field("username") userName: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("last_seen") lastSeen: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("api/v1/report")
    suspend fun report(
        @Field("user_id") user_id: String,
        @Field("book_id") book_id: String,
        @Field("report_type") report_type: String,
        @Field("report_reason") report_reason: String
    ): ReportResponse

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
    @POST("api/v1/follower")
    suspend fun follow(
        @Field("user_id") user_id: String,
        @Field("follower_id") follower_id: String
    ): AddFollowerResponse

    @FormUrlEncoded
    @POST("api/v1/download")
    suspend fun download(
        @Field("user_id") user_id: String,
        @Field("book_id") book_id: String
    ): DownloadBookResponse

    @FormUrlEncoded
    @POST("api/v1/book")
    suspend fun createBook(
        @Field("id") id :Int,
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

    @FormUrlEncoded
    @POST("api/v1/chapter")
    suspend fun createChapter(
        @Field("id") id: Int,
        @Field("title") title: String,
        @Field("book_id") book_id: Int,
        @Field("content") content: String,
        @Field("user_id") user_id: Int,
        @Field("status") status: Int,
        @Field("total_comments") total_comments: Int = 0,
        @Field("total_likes") total_likes: Int = 0,
        @Field("category_id") category_id: Int
    ): PostChapterResponse

    @GET("api/v1/logout")
    suspend fun logout(): LogoutResponse

    @GET("api/v1/categories")
    suspend fun getCategories(): CategoryAllResponse

    @GET("api/v1/category/{id}")
    suspend fun getCategoryBooks(@Path("id") id: Int): BooksByCategoryResponse

    @GET("/api/v1/get_category/{id}")
    suspend fun getCategoryName(@Path("id") id: Int): CategoryResponse

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
    ): PostCommentResponse

    @GET("api/v1/chapters/{id}")
    suspend fun getChapters(@Path("id") id: Int): ChaptersResponse

    @GET("api/v1/chapter/{id}")
    suspend fun getChapter(@Path("id") id: Int): ChapterResponse

    @GET("api/v1/get_profile/{id}")
    suspend fun getProfile(@Path("id") id: Int): AuthorResponse

    @GET("api/v1/get_followers/{id}")
    suspend fun getFollowers(@Path("id") id: Int): FollowersResponse

    @GET("api/v1/books/{id}/{status}")
    suspend fun getBooksByStatus(
        @Path("id") id: Int,
        @Path("status") status: Int
    ): BooksByStatusResponse

    @GET("api/v1/get_profiles/{name}")
    suspend fun getProfilesByName(@Path("name") name: String): ProfilesByNameResponse

    @GET("api/v1/get_books/{name}")
    suspend fun getBooksByName(@Path("name") name: String): BooksByNameResponse

    @FormUrlEncoded
    @POST("api/v1/change_password")
    suspend fun changePassword(
        @Field("user_id") user_id: Int,
        @Field("old_password") old_password: String,
        @Field("new_password") new_password: String
    ): ChangePasswordResponse

    @GET("api/v1/get_books_max_views")
    suspend fun getBooksByMaxViews(): BooksByMaxViewsResponse

    @FormUrlEncoded
    @POST("api/v1/update_comment")
    suspend fun updateComments(
        @Field("id") id: Int
    ): UpdateCommentsResponse

    @FormUrlEncoded
    @POST("api/v1/check_like")
    suspend fun checkLike(
        @Field("user_id") user_id: Int,
        @Field("chapter_id") chapter_id: Int
    ): CheckLikeResponse

    @FormUrlEncoded
    @POST("api/v1/add_like")
    suspend fun updateLike(
        @Field("user_id") user_id: Int,
        @Field("chapter_id") chapter_id: Int,
    ): UpdateLikesResponse

    @FormUrlEncoded
    @POST("api/v1/delete_like")
    suspend fun deleteLike(
        @Field("user_id") user_id: Int,
        @Field("chapter_id") chapter_id: Int,
    ): DeleteLikeResponse

    //update views in books
    @FormUrlEncoded
    @POST("api/v1/update_views")
    suspend fun updateViews(
        @Field("id") id: Int
    ): UpdateBookViewsResponse

    //add books in read later
    @FormUrlEncoded
    @POST("api/v1/add_reading_later")
    suspend fun addReadLater(
        @Field("user_id") user_id: Int,
        @Field("book_id") book_id: Int,
    ): AddToReadLaterResponse

    @FormUrlEncoded
    @POST("api/v1/check_follow")
    suspend fun checkFollower(
        @Field("user_id") user_id: Int,
        @Field("follower_id") follower_id: Int,
    ): CheckFollowResponse


    @GET("api/v1/get_reading_list/{id}")
    suspend fun getReadingList(@Path("id") id: Int): ReadingListResponse

    //delete book
    @FormUrlEncoded
    @POST("api/v1/delete_book")
    suspend fun deleteBook(
        @Field("id") id: Int
    ): DeleteBookResponse

    //delete chapter
    @FormUrlEncoded
    @POST("api/v1/delete_chapter")
    suspend fun deleteChapter(
        @Field("id") id: Int
    ): DeleteChapterResponse

    //unfollow user
    @FormUrlEncoded
    @POST("api/v1/unfollow")
    suspend fun unfollow(
        @Field("user_id") user_id: Int,
        @Field("follower_id") follower_id: Int,
    ): UnfollowResponse

    //block user
    @FormUrlEncoded
    @POST("api/v1/block")
    suspend fun block(
        @Field("user_id") user_id: Int,
        @Field("blocked_id") follower_id: Int,
    ): BlockResponse

    //get list of blocked users
    @GET("api/v1/get_blocked/{id}")
    suspend fun getBlocked(@Path("id") id: Int): BlockedResponse

    //get favourite books
    @GET("api/v1/get_favourite/{id}")
    suspend fun getFavourite(@Path("id") id: Int): FavouriteResponse

    @FormUrlEncoded
    @POST("api/v1/delete_favourite")
    suspend fun deleteFavourite(
        @Field("user_id") user_id: Int,
        @Field("book_id") book_id: Int,
    ): DeleteFavourite

    @FormUrlEncoded
    @POST("api/v1/delete_readinglater")
    suspend fun deleteReadLater(
        @Field("user_id") user_id: Int,
        @Field("book_id") book_id: Int,
    ): DeleteReadLaterResponse


}
object ApiClient {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
