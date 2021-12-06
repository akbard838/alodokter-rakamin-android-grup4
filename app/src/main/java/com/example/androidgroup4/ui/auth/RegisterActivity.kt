package com.example.androidgroup4.ui.auth

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.databinding.ActivityRegisterBinding
import com.example.androidgroup4.ui.success.SuccessActivity
import com.example.androidgroup4.utils.*
import com.example.androidgroup4.utils.enum.SuccessType

class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {

    companion object {
        fun start(context: Context) {
            Intent(context, RegisterActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

    override val bindingInflater: (LayoutInflater) -> ViewBinding = ActivityRegisterBinding::inflate

    override fun initIntent() {

    }

    override fun initUI() {
        setupToolbar(binding.toolbarMain.toolbar, true, emptyString())

        initDummyData()
    }

    override fun initAction() {
        binding.apply {
            btnRegister.setOnClickListener {
                tilEmail.validateEmail()
                tilPassword.validatePassword()
                tilConfirmPassword.validateConfirmPassword(edtPassword.text.toString())

                isFormValid(listOf(tilEmail, tilPassword, tilConfirmPassword)) {
                    SuccessActivity.start(
                        this@RegisterActivity,
                        R.drawable.ic_done,
                        getString(R.string.title_register_success),
                        getString(R.string.message_input_account_for_login),
                        getString(R.string.button_login),
                        SuccessType.REGISTER.type
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

    private fun initDummyData() {
        binding.apply {
            edtEmail.setText("akbard838@gmail.com")
            edtPassword.setText("Dino123")
            edtConfirmPasword.setText("Dino123")
        }
    }

}