package com.example.androidgroup4.utils

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.example.androidgroup4.utils.constant.PreferenceKeys
import com.google.gson.Gson

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

fun <T> getObjectPreference(
    context: Context,
    preferenceKey: String?,
    classType: Class<T>?
): T? {
    val sharedPreferences = context.getSharedPreferences(PreferenceKeys.PREFERENCE_NAME, 0)
    if (sharedPreferences.contains(preferenceKey)) {
        val gson = Gson()
        return gson.fromJson(sharedPreferences.getString(preferenceKey, ""), classType)
    }
    return null
}

fun hideSoftKeyboard(context: Context, input: EditText) {
    val imm: InputMethodManager? =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.hideSoftInputFromWindow(input.windowToken, 0)
}

fun String.toHttps(): String {
    if (!this.contains("https")) {
        return this.replace("http", "https")
    }
    return this
}