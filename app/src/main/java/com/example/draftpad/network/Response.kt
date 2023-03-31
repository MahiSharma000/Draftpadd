package com.example.draftpad.network

data class RegisterResponse(
    val status: String,
    val msg: String
)

data class UserDataResponse(
    val status: String
)

data class ReportResponse(
    val status: String,
    val msg: String
)

data class LoginResponse(
    val status: String,
    val username: String,
    val email: String,
    val id: String
)

data class LogoutResponse(
    val status: String
)

data class CategoryAllResponse(
    val status: String,
    val categories: List<Category>
)

data class BooksByCategoryResponse(
    val status: String,
    val books: List<Book>
)


data class BooksAllResponse(
    val status: String,
    val books: List<Book>
)

data class PostBookResponse(
    val status: String,
    val msg: String,
    val id: Int
)

data class SelectedBookResponse(
    val status: String,
    val book: Book
)

data class GetCommentsResponse(
    val status: String,
    val comments: List<Comment>
)

data class ChaptersResponse(
    val status: String,
    val chapters: List<Chapter>
)

data class ChapterResponse(
    val status: String,
    val chapter: Chapter
)

data class AuthorResponse(
    val status: String,
    val author: UserProfile
)

data class PostCommentResponse(
    val status: String,
    val msg: String,
)

data class FollowerResponse(
    val status: String,
    val followers: List<Follower>
)

data class PostChapterResponse(
    val status: String
)

data class BooksByStatusResponse(
    val status: String,
    val books: List<Book>
)

data class AddFollowerResponse(
    val status: String,
    val msg: String
)

data class ProfilesByNameResponse(
    val status: String,
    val profiles: List<UserProfile>
)

data class BooksByNameResponse(
    val status: String,
    val books: List<Book>
)

data class DownloadBookResponse(
    val status: String,
    val msg: String
)

data class ReadingListByNameResponse(
    val status: String,
    val readingList: List<ReadingList>
)

data class ChangePasswordResponse(
    val status: String,
    val msg: String
)

data class BooksByMaxViewsResponse(
    val status: String,
    val books: List<Book>
)

data class UpdateCommentsResponse(
    val status: String

)

data class CheckLikeResponse(
    val status: String
)
data class UpdateLikesResponse(
    val status: String
)

data class DeleteLikeResponse(
    val status: String
)

data class CategoryResponse(
    val category: Category,
    val status: String
)

data class UpdateBookViewsResponse(
    val status: String
)

data class FollowersResponse(
    val status: String,
    val followers: List<UserProfile>
)