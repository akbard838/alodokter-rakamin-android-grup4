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
import com.example.androidgroup4.utils.*
import com.example.androidgroup4.utils.constant.BundleKeys
import com.example.androidgroup4.utils.enum.GenderType
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
        profile = intent.getParcelableExtra(BundleKeys.PROFILE)
    }

    override fun initUI() {
        setupToolbar(binding.toolbarMain.toolbar, true, getString(R.string.label_edit_profile))
    }

    override fun initAction() {
        binding.apply {
            btnChangeData.setOnClickListener {
                tilKtpNumber.validateConfirmIdNumber()
                tilFullName.validateNonEmpty()

                isFormValid(listOf(tilKtpNumber, tilFullName)) {
                    finish()
                }
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
            btnMale.setOnClickListener {
                changeGenderToMale()
            }
            btnFemale.setOnClickListener {
                changeGenderToFemale()
            }
        }
    }

    override fun initProcess() {
        initDummyData()
    }

    override fun initObservable() {

    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        binding.edtBirthDate.setText(dateFormat.format(calendar.time))

        dueDateMillis = calendar.timeInMillis
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
            if (profile?.gender == GenderType.MALE.type) btnMale.isChecked = true
            else btnFemale.isChecked = true

            edtFullName.setText(profile?.name)
            edtBirthDate.setText(profile?.birthDate)
            edtKtpNumber.setText(profile?.ktpNumber)
            edtAddress.setText(profile?.address)
        }
    }

    private fun showDatePicker() {
        val dialogFragment = DatePickerFragment()
        dialogFragment.show(supportFragmentManager, "datePicker")
    }

    private fun changeGenderToFemale(){
        binding.btnMale.isChecked = false
        binding.btnFemale.isChecked = true
    }

    private fun changeGenderToMale(){
        binding.btnMale.isChecked = true
        binding.btnFemale.isChecked = false
    }

}