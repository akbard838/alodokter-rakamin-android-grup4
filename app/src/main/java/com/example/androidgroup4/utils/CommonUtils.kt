package com.example.androidgroup4.utils

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.widget.Toast
import com.example.androidgroup4.utils.constant.PreferenceKeys

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

fun showToast(context: Context, message: String?) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun getAppSharedPreference(context: Context): SharedPreferences {
    return context.getSharedPreferences(PreferenceKeys.PREFERENCE_NAME, Context.MODE_PRIVATE)
}

fun getAppPreferenceEditor(context: Context): SharedPreferences.Editor {
    return context.getSharedPreferences(PreferenceKeys.PREFERENCE_NAME, Context.MODE_PRIVATE).edit()
}