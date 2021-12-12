package com.example.androidgroup4.ui.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.data.source.remote.network.ApiResponse
import com.example.androidgroup4.data.source.remote.response.UserResponse
import com.example.androidgroup4.databinding.ActivityChangePasswordBinding
import com.example.androidgroup4.ui.UserViewModel
import com.example.androidgroup4.utils.*
import com.example.androidgroup4.utils.constant.PreferenceKeys
import kotlinx.coroutines.flow.collect

class ChangePasswordActivity : BaseActivity<ActivityChangePasswordBinding>() {

    companion object {
        fun start(context: Context) {
            Intent(context, ChangePasswordActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }
    private val sharedPreferences: SharedPreferences = applicationContext.getSharedPreferences(PreferenceKeys.PREFERENCE_NAME, Context.MODE_PRIVATE)
    private val password = sharedPreferences.getString(PreferenceKeys.PASSWORD, "Password")

    private var user: UserResponse? = null

    private val userViewModel: UserViewModel by viewModels()

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
                if (password != null) {
                    Log.d("Password", password)
                }
                tilOldPassword.validateOldPassword(PreferenceKeys.PASSWORD)
                tilNewPassword.validatePassword()
                tilConfirmPassword.validateConfirmPassword(edtNewPassword.text.toString())

                isFormValid(listOf(tilOldPassword, tilNewPassword, tilConfirmPassword)) {
                    putChangePassword()
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

    private fun putChangePassword(){
        lifecycleScope.launchWhenStarted {
            userViewModel.putChangePassword(
                userId = user?.user_id?.toInt() ?: 0,
                password = binding.edtNewPassword.toString(),
            ).collect {
                when (it) {
                    is ApiResponse.Success -> {
                        hideLoading()
                        showToast(this@ChangePasswordActivity, "Password berhasil diubah")
                        finish()
                    }
                    is ApiResponse.Failure -> {
                        hideLoading()
                        showToast(this@ChangePasswordActivity, it.message)
                    }
                    is ApiResponse.Loading -> {
                        showLoading()
                    }
                }
            }
        }
    }

}