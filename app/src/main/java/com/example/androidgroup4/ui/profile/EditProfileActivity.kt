package com.example.androidgroup4.ui.profile

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.data.model.Doctor
import com.example.androidgroup4.data.model.Profile
import com.example.androidgroup4.databinding.ActivityEditProfileBinding
import com.example.androidgroup4.utils.DatePickerFragment
import com.example.androidgroup4.utils.constant.BundleKeys
import com.example.androidgroup4.utils.setImageUrl
import java.text.SimpleDateFormat
import java.util.*

class EditProfileActivity : BaseActivity<ActivityEditProfileBinding>(), DatePickerFragment.DialogDateListener {

    private var dueDateMillis: Long = System.currentTimeMillis()

    companion object {
        fun start(context: Context?, profile: Profile) {
            Intent(context, EditProfileActivity::class.java).apply {
                putExtra(BundleKeys.PROFILE, profile)
                context?.startActivity(this)
            }
        }
    }

    private var profile: Profile? = null

    override val bindingInflater: (LayoutInflater) -> ViewBinding =
        ActivityEditProfileBinding::inflate

    override fun initIntent() {
        profile = intent.getParcelableExtra("PROFILE")
    }

    override fun initUI() {
        setupToolbar(binding.toolbarMain.toolbar, true, getString(R.string.label_edit_profile))
        initDummyData()
    }

    override fun initAction() {
        binding.apply {
            btnChangeData.setOnClickListener {

            }
            ibDatePicker.setOnClickListener {
                showDatePicker()
            }
            edtBirthDate.setOnClickListener {
                showDatePicker()
            }
            tilBirthDate.setOnClickListener {
                showDatePicker()
            }
            btnMale.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    btnFemale.isChecked = false
                } else {
                    btnMale.isChecked = false
                }
            }
            btnFemale.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    btnMale.isChecked = false
                } else {
                    btnFemale.isChecked = false
                }
            }
        }
    }

    override fun initProcess() {
        binding.apply {
            edtFullName.setText(profile?.name)
            edtBirthDate.setText(profile?.birthDate)
            edtKtpNumber.setText(profile?.ktpNumber)
            edtAddress.setText(profile?.address)
        }
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

    }

    private fun showDatePicker() {
        val dialogFragment = DatePickerFragment()
        dialogFragment.show(supportFragmentManager, "datePicker")
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        binding.edtBirthDate.setText(dateFormat.format(calendar.time))

        dueDateMillis = calendar.timeInMillis
    }

}