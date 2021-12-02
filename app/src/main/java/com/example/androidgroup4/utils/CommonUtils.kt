package com.example.androidgroup4.utils

import android.content.Context
import android.view.View
import android.widget.Toast

fun emptyString() = ""

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun showToast(context: Context, message: String?){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}