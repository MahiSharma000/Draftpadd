package com.example.draftpad

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
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