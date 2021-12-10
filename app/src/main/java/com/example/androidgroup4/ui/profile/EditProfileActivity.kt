package com.example.androidgroup4.ui.profile

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
import com.example.androidgroup4.data.source.remote.response.UserResponse
import com.example.androidgroup4.databinding.ActivityEditProfileBinding
import com.example.androidgroup4.ui.UserViewModel
import com.example.androidgroup4.utils.*
import com.example.androidgroup4.utils.constant.BundleKeys
import com.example.androidgroup4.utils.enum.GenderType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class EditProfileActivity : BaseActivity<ActivityEditProfileBinding>(),
    DatePickerFragment.DialogDateListener {

    private val userViewModel: UserViewModel by viewModels()
    private var dueDateMillis: Long = System.currentTimeMillis()

    companion object {
        fun start(context: Context?, user: UserResponse) {
            Intent(context, EditProfileActivity::class.java).apply {
                putExtra(BundleKeys.PROFILE, user)
                context?.startActivity(this)
            }
        }
    }

    private var user: UserResponse? = null

    override val bindingInflater: (LayoutInflater) -> ViewBinding =
        ActivityEditProfileBinding::inflate

    override fun initIntent() {
        user = intent.getParcelableExtra(BundleKeys.PROFILE)
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
                    putEditProfile()
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

    private fun initUserData() {
        binding.apply {
            if (user?.jenis_kelamin == GenderType.MALE.type) btnMale.isChecked = true
            else btnFemale.isChecked = true

            edtFullName.setText(user?.fullname)
            edtBirthDate.setText(getBirthDateFormat(user?.tgl_lahir ?: emptyString()))
            edtKtpNumber.setText(user?.no_ktp.toString())
            edtAddress.setText(user?.alamat)
        }
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

    private fun putEditProfile() {
        lifecycleScope.launchWhenStarted {
            userViewModel.putEditProfile(
                userId = user?.user_id?.toInt() ?: 0,
                fullName = binding.edtFullName.text.toString(),
                dateOfBirth = binding.edtBirthDate.text.toString(),
                gender = if (binding.btnMale.isChecked) GenderType.MALE.type else GenderType.FEMALE.type,
                idCardNumber = binding.edtKtpNumber.text.toString().toLong(),
                address = binding.edtAddress.text.toString()
            ).collect {
                when (it) {
                    is ApiResponse.Success -> {
                        hideLoading()
                        showToast(this@EditProfileActivity, "Data profil berhasil diubah")
                        finish()
                    }
                    is ApiResponse.Failure -> {
                        hideLoading()
                        showToast(this@EditProfileActivity, it.message)
                    }
                    is ApiResponse.Loading -> {
                        showLoading()
                    }
                }
            }
        }
    }

}