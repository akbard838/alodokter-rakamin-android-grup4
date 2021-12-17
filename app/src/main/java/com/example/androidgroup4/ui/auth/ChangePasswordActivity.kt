package com.example.androidgroup4.ui.auth

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.data.model.User
import com.example.androidgroup4.data.user.model.request.ChangePasswordRequest
import com.example.androidgroup4.data.user.model.request.UserRequest
import com.example.androidgroup4.databinding.ActivityChangePasswordBinding
import com.example.androidgroup4.ui.main.MainActivity
import com.example.androidgroup4.ui.viewmodel.UserViewModel
import com.example.androidgroup4.utils.*
import com.example.androidgroup4.utils.enum.GenderType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordActivity : BaseActivity<ActivityChangePasswordBinding>() {

    companion object {
        fun start(context: Context) {
            Intent(context, ChangePasswordActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

    private val userViewModel: UserViewModel by viewModels()

    private var path: String? = emptyString()

    override val bindingInflater: (LayoutInflater) -> ViewBinding =
        ActivityChangePasswordBinding::inflate

    override fun initIntent() {
        val uri = intent.data
        uri?.let {
            path = it.toString().substringAfterLast("=")
        }
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
                tilNewPassword.validatePassword()
                tilConfirmPassword.validateConfirmPassword(edtNewPassword.text.toString())

                isFormValid(listOf(tilNewPassword, tilConfirmPassword)) {
                    userViewModel.putChangePassword(
                        ChangePasswordRequest(
                            email = "test@gmail.com",
                            password = binding.edtNewPassword.toString()
                        )
                    )
                }
            }
        }
    }

    override fun initProcess() {

    }

    override fun initObservable() {
        userViewModel.change.observe(this, {
            when (it) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    hideLoading()
                    showToast(
                        this@ChangePasswordActivity,
                        getString(R.string.message_change_password_success)
                    )
                    finish()
                }
                is Resource.Error -> {
                    hideLoading()
                    Toast.makeText(this, it.apiError.message, Toast.LENGTH_SHORT).show()
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