package com.example.draftpad

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
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