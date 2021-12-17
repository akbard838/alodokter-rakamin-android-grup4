package com.example.androidgroup4.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.base.BaseFragment
import com.example.androidgroup4.data.model.User
import com.example.androidgroup4.databinding.FragmentProfileBinding
import com.example.androidgroup4.ui.auth.ChangePasswordActivity
import com.example.androidgroup4.ui.auth.LoginActivity
import com.example.androidgroup4.ui.main.MainActivity
import com.example.androidgroup4.utils.*
import com.example.androidgroup4.utils.constant.PreferenceKeys
import com.example.androidgroup4.utils.constant.PreferenceKeys.IS_LOGIN
import java.util.*

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private var user: User? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding =
        FragmentProfileBinding::inflate

    override fun initIntent() {

    }

    override fun initUI() {
        showLoggedInStateView()
    }

    override fun initAction() {
        binding.apply {
            fabEditProfile.setOnClickListener {
                user?.let {
                    EditProfileActivity.start(this@ProfileFragment.context, it)
                }
            }

            btnChangePassword.setOnClickListener {
                user?.let { ChangePasswordActivity.start(requireContext(), it.email) }
            }

            btnLogout.setOnClickListener {
                getAppPreferenceEditor(requireContext()).remove(PreferenceKeys.USER).apply()
                getAppPreferenceEditor(requireContext()).putBoolean(IS_LOGIN, false).apply()
                MainActivity.start(requireContext())
                activity?.finish()
            }

            layoutNotLoggedIn.btnLogin.setOnClickListener {
                LoginActivity.start(requireContext())
            }
        }
    }

    override fun initProcess() {

    }

    override fun initObservable() {

    }

    override fun onResume() {
        super.onResume()
        if (MainActivity.getUserLoggedInStatus(requireContext())){
            initDataUser()
        }
    }

    private fun showLoggedInStateView() {
        binding.apply {
            if (MainActivity.getUserLoggedInStatus(requireContext())) {
                groupProfile.visible()
                binding.layoutNotLoggedIn.clParent.gone()
            } else {
                groupProfile.gone()
                binding.layoutNotLoggedIn.clParent.visible()
            }
        }
    }

    private fun initDataUser() {
        user = getObjectPreference(requireContext(), PreferenceKeys.USER, User::class.java)
        binding.apply {
            tvName.text = user?.name
            tvEmail.text = user?.email
            tvDateOfBirth.text = user?.birthDate
            tvIdCard.text = user?.idCardNumber
            tvAddress.text = user?.address
        }
    }

}