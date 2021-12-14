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
import com.example.androidgroup4.data.user.model.request.RegisterRequest
import com.example.androidgroup4.databinding.ActivityRegisterBinding
import com.example.androidgroup4.ui.success.SuccessActivity
import com.example.androidgroup4.ui.viewmodel.UserViewModel
import com.example.androidgroup4.utils.*
import com.example.androidgroup4.utils.enum.GenderType
import com.example.androidgroup4.utils.enum.SuccessType
import dagger.hilt.android.AndroidEntryPoint

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

        binding.btnMale.isChecked = true
        initDummyData()
    }

    override fun initAction() {
        binding.apply {
            btnRegister.setOnClickListener {
                tilFullName.validateNonEmpty()
                tilEmail.validateEmail()
                tilPassword.validatePassword()
                tilPassword.validateMinLength(8)
                tilConfirmPassword.validateConfirmPassword(edtPassword.text.toString())

                isFormValid(listOf(tilFullName, tilEmail, tilPassword, tilConfirmPassword)) {
                    userViewModel.postRegister(
                        RegisterRequest(
                            name = binding.edtFullName.text.toString(),
                            email = binding.edtEmail.text.toString(),
                            password = binding.edtPassword.text.toString(),
                            gender = if (binding.btnMale.isChecked) GenderType.MALE.type else GenderType.FEMALE.type
                        )
                    )
                }
            }

            btnMale.setOnClickListener {
                changeGenderToMale()
            }

            btnFemale.setOnClickListener {
                changeGenderToFemale()
            }
        }
    }

    override fun initProcess() {

    }

    override fun initObservable() {
        userViewModel.register.observe(this, {
            when (it) {
                is Resource.Loading -> {
                    showLoading()
                    binding.tvError.gone()
                }
                is Resource.Success -> {
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
                is Resource.Error -> {
                    hideLoading()
                    if (it.apiError.message == getString(R.string.error_email_exist)) {
                        binding.tvError.text = getString(R.string.error_email_exist_id)
                        binding.tvError.visible()
                    } else {
                        Toast.makeText(this, it.apiError.message, Toast.LENGTH_SHORT).show()
                    }
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

    private fun changeGenderToFemale() {
        binding.btnMale.isChecked = false
        binding.btnFemale.isChecked = true
    }

    private fun changeGenderToMale() {
        binding.btnMale.isChecked = true
        binding.btnFemale.isChecked = false
    }

    private fun initDummyData() {
        binding.apply {
            edtFullName.setText("Dino Akbar")
            edtEmail.setText("akbard838@gmail.com")
            edtPassword.setText("Dino1234")
            edtConfirmPasword.setText("Dino1234")
        }
    }

}