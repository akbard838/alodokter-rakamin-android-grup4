package com.example.androidgroup4.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseFragment
import com.example.androidgroup4.databinding.FragmentProfileBinding
import com.example.androidgroup4.utils.setImageUrl

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding =
        FragmentProfileBinding::inflate

    override fun initIntent() {

    }

    override fun initUI() {
        initDataUser()
    }

    override fun initAction() {
        binding.apply {
            changePhoto.setOnClickListener {

            }
            editProfile.setOnClickListener {

            }
            btnChangePassword.setOnClickListener {

            }
            btnLogout.setOnClickListener {

            }
        }
    }

    override fun initProcess() {
    }

    override fun initObservable() {
    }

    private fun initDataUser() {
        binding.apply {
            imgProfileUser.setImageUrl(
                requireContext(),
                getString(R.string.sample_image_url),
                pbProfileUser,
                R.drawable.img_not_available
            )
            tvName.text = getString(R.string.sample_name)
            tvEmail.text = getString(R.string.sample_email)
            tvDateOfBirth.text = getString(R.string.sample_date_of_birth)
            tvIdCard.text = getString(R.string.sample_id_card_number)
            tvAddress.text = getString(R.string.sample_address)
        }
    }

}