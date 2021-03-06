package com.example.androidgroup4.utils

import androidx.annotation.NonNull
import com.example.androidgroup4.R
import com.google.android.material.textfield.TextInputLayout
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import java.util.*

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
    } else if (this.editText?.text.toString().length < 8) {
        this.validateMinLength(8)
    }
    else {
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
        else this.error = resources.getString(R.string.message_error_confirm_password)
    }
}

fun TextInputLayout.validateOldPassword(password: String) {
    if (this.editText?.text.toString().isEmpty()) {
        this.validateNonEmpty()
    } else {
        if (this.editText?.text.toString() == password) this.isErrorEnabled = false
        else this.error = resources.getString(R.string.message_error_old_password)
    }
}

fun TextInputLayout.validateConfirmIdNumber() {
    if (this.editText?.text.toString().isEmpty()) {
        this.validateNonEmpty()
    } else {
        if (this.editText?.text.toString()
                .validator()
                .validNumber()
                .minLength(16)
                .maxLength(16)
                .check()
        ) this.isErrorEnabled = false
        else this.error = resources.getString(R.string.message_error_id_number)
    }
}

fun TextInputLayout.validateMinLength(minLength: Int) {
    if (this.editText?.text.toString().isEmpty()) {
        this.validateNonEmpty()
    } else {
        if (this.editText?.text.toString()
                .validator()
                .minLength(minLength)
                .check()
        ) this.isErrorEnabled = false
        else this.error =
            String.format(resources.getString(R.string.format_error_min_length), minLength)
    }
}

fun isFormValid(textInputLayout: TextInputLayout, @NonNull formValidListener: () -> Unit) {
    if (!textInputLayout.isErrorEnabled) formValidListener.invoke()
}

fun isFormValid(
    listTextInputLayout: List<TextInputLayout>,
    @NonNull formValidListener: () -> Unit
) {
    var isValid = true
    listTextInputLayout.forEach {
        if (it.isErrorEnabled) isValid = false
    }

    if (isValid) formValidListener.invoke()
}