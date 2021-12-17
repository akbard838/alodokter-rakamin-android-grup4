package com.example.androidgroup4.ui.doctor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.databinding.ActivityPatientBinding
import com.example.androidgroup4.ui.main.MainActivity
import com.example.androidgroup4.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class PatientActivity : BaseActivity<ActivityPatientBinding>(),
    DatePickerFragment.DialogDateListener {

    private var dueDateMillis: Long = System.currentTimeMillis()

    companion object {
        fun start(context: Context) {
            Intent(context, PatientActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

    override val bindingInflater: (LayoutInflater) -> ViewBinding =
        ActivityPatientBinding::inflate

    override fun initIntent() {

    }

    override fun initUI() {
        setupToolbar(binding.toolbarMain.toolbar, true, getString(R.string.label_patient_page))
        binding.btnMale.isChecked = true
    }

    override fun initAction() {
        binding.apply {
            btnChangeData.setOnClickListener {
                tilKtpNumber.validateConfirmIdNumber()
                tilFullName.validateNonEmpty()

                isFormValid(listOf(tilKtpNumber, tilFullName)) {
                    showToast(this@PatientActivity, "Konsultasi berhasil diajukan")
                    MainActivity.start(this@PatientActivity)
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
        initUserData()
    }

    override fun initObservable() {

    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat(getString(R.string.format_birth_date), Locale.getDefault())
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

    private fun initUserData() {

    }

    private fun showDatePicker() {
        val dialogFragment = DatePickerFragment()
        dialogFragment.show(supportFragmentManager, "datePicker")
    }

    private fun changeGenderToFemale() {
        binding.btnMale.isChecked = false
        binding.btnFemale.isChecked = true
    }

    private fun changeGenderToMale() {
        binding.btnMale.isChecked = true
        binding.btnFemale.isChecked = false
    }

}