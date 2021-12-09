package com.example.androidgroup4.ui.auth

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.data.source.remote.network.ApiResponse
import com.example.androidgroup4.databinding.ActivityRegisterBinding
import com.example.androidgroup4.ui.UserViewModel
import com.example.androidgroup4.ui.success.SuccessActivity
import com.example.androidgroup4.utils.*
import com.example.androidgroup4.utils.enum.SuccessType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {

    companion object {
        fun start(context: Context) {
            Intent(context, RegisterActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

    private val userViewModel: UserViewModel by viewModels()

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
                    postRegister()
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

    private fun postRegister() {
        lifecycleScope.launchWhenStarted {
            userViewModel.postRegister(
                binding.edtEmail.text.toString(),
                binding.edtPassword.text.toString()
            ).collect {
                when (it) {
                    is ApiResponse.Success -> {
                        hideLoading()
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
                    is ApiResponse.Failure -> {
                        hideLoading()
                        showToast(this@RegisterActivity, it.message)
                    }
                    is ApiResponse.Loading -> {
                        showLoading()
                    }
                }
            }
        }
    }

    private fun initDummyData() {
        binding.apply {
            edtEmail.setText("akbard838@gmail.com")
            edtPassword.setText("Dino123")
            edtConfirmPasword.setText("Dino123")
        }
    }

}