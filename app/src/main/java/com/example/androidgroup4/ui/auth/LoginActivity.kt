package com.example.androidgroup4.ui.auth

import android.util.Patterns
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.databinding.ActivityLoginBinding
import com.example.androidgroup4.utils.showToast
import com.example.androidgroup4.utils.validateEmail
import com.example.androidgroup4.utils.validateNonEmpty
import com.google.common.collect.Range

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private lateinit var validation: AwesomeValidation

    override val bindingInflater: (LayoutInflater) -> ViewBinding = ActivityLoginBinding::inflate

    override fun initIntent() {

    }

    override fun initUI() {
        validation = AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT)

        validation.addValidation(this, R.id.tilEmail, Patterns.EMAIL_ADDRESS, R.string.button_login)
        validation.addValidation(this, R.id.tilPassword, Range.closed(0.0f, 3f), R.string.button_login)

    }

    override fun initAction() {
        binding.apply {
            btnForgotPassword.setOnClickListener {
                showToast(this@LoginActivity, "Forgot")
            }

            btnLogin.setOnClickListener {
                tilEmail.validateEmail()
                tilPassword.validateNonEmpty()
            }

            btnRegister.setOnClickListener {

            }
        }
    }

    override fun initProcess() {

    }

    override fun initObservable() {

    }

}