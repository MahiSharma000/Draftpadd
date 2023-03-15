package com.example.draftpad

import android.util.Base64
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.draftpad.ui.read.CommentApiStatus
import com.example.draftpad.ui.search.BookApiStatus
import com.example.draftpad.ui.search.SearchApiStatus

@BindingAdapter("searchApiStatus")
fun bindStatus(statusImageView: ImageView, status: SearchApiStatus?) {
    when (status) {
        SearchApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.load(R.drawable.ic_connection_error)
        }
        SearchApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.load(R.drawable.loading_animation)
        }
        SearchApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
        else -> {

        }
    }
}

@BindingAdapter("commentApiStatus")
fun bindStatus(statusImageView: ImageView, status: CommentApiStatus?) {
    when (status) {
        CommentApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.load(R.drawable.ic_connection_error)
        }
        CommentApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.load(R.drawable.loading_animation)
        }
        CommentApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
        else -> {

        }
    }
}

@BindingAdapter("bookApiStatus")
fun bindStatus(statusImageView: ImageView, status: BookApiStatus?) {
    when (status) {
        BookApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.load(R.drawable.ic_connection_error)
        }
        BookApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.load(R.drawable.loading_animation)
        }
        BookApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
        else -> {

        }
    }
}

@BindingAdapter("summary_text")
fun bindSummaryText(textView: TextView, text: String?) {
    if (text != null) {
        textView.text = summary(text, 50)
    } else {
        textView.text = "no description"
    }
}

fun summary(text: String, max: Int): String {
    return if (text.length > max) {
        text.substring(0, max) + "..."
    } else {
        text
    }
}

@BindingAdapter("author_img")
fun bindAuthorImage(imageView: ImageView, data: String?) {
    if (data != null) {
        // if data is url then load
        if (data.contains("http")) {
            imageView.load(data)
        } else {
            // convert base64 to bitmap
            val decodedString: ByteArray = Base64.decode(data, Base64.DEFAULT)
            imageView.load(decodedString)
        }
    }
}