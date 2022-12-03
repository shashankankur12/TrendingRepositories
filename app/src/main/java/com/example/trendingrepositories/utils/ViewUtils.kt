package com.example.trendingrepositories.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.snackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE).also { snackbar ->
        snackbar.setAction("Ok") {
            snackbar.dismiss()
        }

    }.show()
}