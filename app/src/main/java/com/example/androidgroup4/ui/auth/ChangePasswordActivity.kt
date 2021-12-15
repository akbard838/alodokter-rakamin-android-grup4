package com.example.androidgroup4.ui.auth

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.databinding.ActivityChangePasswordBinding
import com.example.androidgroup4.utils.isFormValid
import com.example.androidgroup4.utils.validateConfirmPassword
import com.example.androidgroup4.utils.validatePassword

class ChangePasswordActivity : BaseActivity<ActivityChangePasswordBinding>() {

    companion object {
        fun start(context: Context) {
            Intent(context, ChangePasswordActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

    override val bindingInflater: (LayoutInflater) -> ViewBinding =
        ActivityChangePasswordBinding::inflate

    override fun initIntent() {

    }

    override fun initUI() {
        binding.apply {
            setupToolbar(
                toolbarChangePassword.toolbar, true, getString(R.string.title_change_password)
            )
        }

    }

    override fun initAction() {
        binding.apply {
            btnChangePassword.setOnClickListener {
                tilOldPassword.validatePassword()
                tilNewPassword.validatePassword()
                tilConfirmPassword.validateConfirmPassword(edtNewPassword.text.toString())

                isFormValid(listOf(tilOldPassword, tilNewPassword, tilConfirmPassword)) {

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
/*
    private fun putChangePassword(){
        lifecycleScope.launchWhenStarted {
            userViewModel.putChangePassword(
                userId = user?.user_id?.toInt() ?: 0,
                password = binding.edtNewPassword.toString(),
            ).collect {
                when (it) {
                    is Resource.Success -> {
                        hideLoading()
                        showToast(this@ChangePasswordActivity, "Password berhasil diubah")
                        finish()
                    }
                    is Resource.Failure -> {
                        hideLoading()
                        showToast(this@ChangePasswordActivity, it.message)
                    }
                    is Resource.Loading -> {
                        showLoading()
                    }
                }
            }
        }
    }
*/
}