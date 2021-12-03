package com.example.androidgroup4.ui.success

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.databinding.ActivitySuccessBinding
import com.example.androidgroup4.ui.auth.LoginActivity
import com.example.androidgroup4.utils.constant.BundleKeys
import com.example.androidgroup4.utils.emptyString
import com.example.androidgroup4.utils.enum.SuccessType

class SuccessActivity : BaseActivity<ActivitySuccessBinding>() {

    companion object {

        fun start(
            context: Context,
            imageResource: Int,
            title: String,
            message: String,
            textButton: String,
            successType: String
        ) {
            Intent(context, SuccessActivity::class.java).apply {
                putExtra(BundleKeys.IMAGE, imageResource)
                putExtra(BundleKeys.MESSAGE, message)
                putExtra(BundleKeys.TITLE, title)
                putExtra(BundleKeys.TEXT_BUTTON, textButton)
                putExtra(BundleKeys.SUCCESS_TYPE, successType)
                context.startActivity(this)
            }
        }
    }

    private var imageResource: Int = 0
    private var title: String = emptyString()
    private var message: String = emptyString()
    private var textButton: String = emptyString()
    private var successType: String = emptyString()

    override val bindingInflater: (LayoutInflater) -> ViewBinding = ActivitySuccessBinding::inflate

    override fun initIntent() {
        imageResource = intent.getIntExtra(BundleKeys.IMAGE, 0)
        title = intent.getStringExtra(BundleKeys.TITLE) ?: emptyString()
        message = intent.getStringExtra(BundleKeys.MESSAGE) ?: emptyString()
        textButton = intent.getStringExtra(BundleKeys.TEXT_BUTTON) ?: emptyString()
        successType = intent.getStringExtra(BundleKeys.SUCCESS_TYPE) ?: emptyString()
    }

    override fun initUI() {
        binding.apply {
            imgSuccess.setImageResource(imageResource)
            tvTitle.text = title
            tvMessage.text = message
            btnSuccess.text = textButton
        }
    }

    override fun initAction() {
        binding.apply {
            btnSuccess.setOnClickListener {
                finish()
            }
        }
    }

    override fun initProcess() {

    }

    override fun initObservable() {

    }

}