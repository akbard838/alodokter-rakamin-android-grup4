package com.example.androidgroup4.utils

import android.widget.EditText
import com.example.androidgroup4.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator

private var isValid = true

fun TextInputLayout.validateEmail() {
    if (this.editText?.text.toString().isEmpty()) {
        this.validateNonEmpty()
    } else {
        if (this.editText?.text.toString().validEmail()) this.isErrorEnabled = false
        else this.error = resources.getString(R.string.message_error_email)
    }
}

fun TextInputLayout.validateNonEmpty() {
    if (this.editText?.text.toString().nonEmpty()) this.isErrorEnabled = false
    else this.error = resources.getString(R.string.message_error_empty_field)
}

fun TextInputLayout.validatePassword() {
    if (this.editText?.text.toString().isEmpty()) {
        this.validateNonEmpty()
    } else {
        if (this.editText?.text.toString()
                .validator()
                .atleastOneNumber()
                .atleastOneUpperCase()
                .check()
        ) this.isErrorEnabled = false
        else this.error = resources.getString(R.string.message_error_password)
    }
}

fun TextInputLayout.validateConfirmPassword(password: String) {
    if (this.editText?.text.toString().isEmpty()) {
        this.validateNonEmpty()
    } else {
        if (this.editText?.text.toString() == password) this.isErrorEnabled = false
        else this.error = resources.getString(R.string.message_error_confirm_email)
    }
}

fun isFormValid(textInputLayout: TextInputLayout): Boolean {
    if (textInputLayout.isErrorEnabled) return false
    return true
}

fun isFormValid(listTextInputLayout: List<TextInputLayout>): Boolean {
    listTextInputLayout.forEach {
        if (it.isErrorEnabled) return false
    }
    return true
}