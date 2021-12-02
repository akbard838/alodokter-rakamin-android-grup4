package com.example.androidgroup4.utils

import com.example.androidgroup4.R
import com.google.android.material.textfield.TextInputLayout
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator

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
                .atleastOneSpecialCharacters()
                .atleastOneUpperCase()
                .check()
        ) this.isErrorEnabled = false
        else this.error = resources.getString(R.string.message_error_email)
    }
}