package com.example.androidgroup4.ui.auth

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.databinding.ActivityForgotPasswordBinding
import com.example.androidgroup4.ui.success.SuccessActivity
import com.example.androidgroup4.ui.viewmodel.UserViewModel
import com.example.androidgroup4.utils.Resource
import com.example.androidgroup4.utils.enum.SuccessType
import com.example.androidgroup4.utils.isFormValid
import com.example.androidgroup4.utils.showToast
import com.example.androidgroup4.utils.validateEmail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordActivity : BaseActivity<ActivityForgotPasswordBinding>() {

    companion object {
        fun start(context: Context) {
            Intent(context, ForgotPasswordActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

    private val userViewModel: UserViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ViewBinding =
        ActivityForgotPasswordBinding::inflate

    override fun initIntent() {

    }

    override fun initUI() {
        binding.apply {
            setupToolbar(
                toolbarResetPassword.toolbar,
                true,
                getString(R.string.title_forget_password)
            )
            edtEmail.setText("akbard838@gmail.com")
        }
    }

    override fun initAction() {
        binding.apply {
            btnResetPassword.setOnClickListener {
                tilEmail.validateEmail()

                isFormValid(tilEmail) {
                    userViewModel.postForgotPassword(edtEmail.text.toString())
                }
            }
        }
    }

    override fun initProcess() {

    }

    override fun initObservable() {
        userViewModel.forgotPassword.observe(this, {
            when (it) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    hideLoading()
                    SuccessActivity.start(
                        this@ForgotPasswordActivity,
                        R.drawable.ic_email_sent,
                        getString(R.string.title_reset_password_success),
                        String.format(
                            getString(R.string.message_reset_password_success),
                            binding.edtEmail.text.toString()
                        ),
                        getString(R.string.button_login),
                        SuccessType.RESET_PASSWORD.type
                    )
                    finish()
                }
                is Resource.Error -> {
                    hideLoading()
                    showToast(this, it.apiError.message)
                }
                else -> {}
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }

}