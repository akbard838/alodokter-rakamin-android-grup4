package com.example.androidgroup4.ui.success

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.databinding.ActivitySuccessBinding
import com.example.androidgroup4.ui.main.MainActivity
import com.example.androidgroup4.utils.constant.BundleKeys
import com.example.androidgroup4.utils.emptyString
import com.example.androidgroup4.utils.enum.SuccessType

class SuccessActivity : BaseActivity<ActivitySuccessBinding>() {

    companion object {

        fun start(context: Context, successType: String, email: String? = emptyString()) {
            Intent(context, SuccessActivity::class.java).apply {
                putExtra(BundleKeys.SUCCESS_TYPE, successType)
                putExtra(BundleKeys.EMAIL, email)
                context.startActivity(this)
            }
        }

    }

    private var successType: String = emptyString()

    private var email: String = emptyString()

    override val bindingInflater: (LayoutInflater) -> ViewBinding = ActivitySuccessBinding::inflate

    override fun initIntent() {
        successType = intent.getStringExtra(BundleKeys.SUCCESS_TYPE) ?: emptyString()
        email = intent.getStringExtra(BundleKeys.EMAIL) ?: emptyString()
    }

    override fun initUI() {
        binding.apply {
            when (successType) {
                SuccessType.REGISTER.type -> {
                    imgSuccess.setImageResource(R.drawable.ic_done)
                    tvTitle.text = getString(R.string.title_register_success)
                    tvMessage.text = getString(R.string.message_input_account_for_login)
                    btnSuccess.text = getString(R.string.button_login)
                }
                SuccessType.RESET_PASSWORD.type -> {
                    imgSuccess.setImageResource(R.drawable.ic_email_sent)
                    tvTitle.text = getString(R.string.title_reset_password_success)
                    tvMessage.text =
                        String.format(getString(R.string.message_reset_password_success), email)
                    btnSuccess.text = getString(R.string.button_login)
                }
                SuccessType.CONSULTATION.type -> {
                    imgSuccess.setImageResource(R.drawable.ic_done)
                    tvTitle.text = getString(R.string.title_consultation_success)
                    tvMessage.text = getString(R.string.message_consultation_success)
                    btnSuccess.text = getString(R.string.button_home)
                }
            }
        }
    }

    override fun initAction() {
        binding.apply {
            btnSuccess.setOnClickListener {
                if (successType == SuccessType.CONSULTATION.type) MainActivity.start(this@SuccessActivity)
                else finish()
            }
        }
    }

    override fun initProcess() {

    }

    override fun initObservable() {

    }

}