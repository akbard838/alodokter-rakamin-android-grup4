package com.example.androidgroup4.ui.auth

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.databinding.ActivityResetPasswordBinding
import com.example.androidgroup4.ui.success.SuccessActivity
import com.example.androidgroup4.utils.enum.SuccessType
import com.example.androidgroup4.utils.isFormValid
import com.example.androidgroup4.utils.validateEmail

class ResetPasswordActivity : BaseActivity<ActivityResetPasswordBinding>() {

    companion object {
        fun start(context: Context) {
            Intent(context, ResetPasswordActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

    override val bindingInflater: (LayoutInflater) -> ViewBinding =
        ActivityResetPasswordBinding::inflate

    override fun initIntent() {

    }

    override fun initUI() {
        binding.apply {
            setupToolbar(toolbarResetPassword.toolbar, true, getString(R.string.title_forget_password))
            edtEmail.setText("akbard838@gmail.com")
        }
    }

    override fun initAction() {
        binding.apply {
            btnResetPassword.setOnClickListener {
                tilEmail.validateEmail()

                if (isFormValid(tilEmail)) {
                    SuccessActivity.start(
                        this@ResetPasswordActivity,
                        R.drawable.ic_email_sent,
                        getString(R.string.title_reset_password_success),
                        String.format(getString(R.string.message_reset_password_success), edtEmail.text.toString()
                        ),
                        getString(R.string.button_login),
                        SuccessType.RESET_PASSWORD.type
                    )
                    finish()
                }
            }
        }
    }

    override fun initProcess() {

    }

    override fun initObservable() {

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