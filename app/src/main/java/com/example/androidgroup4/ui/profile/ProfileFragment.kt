package com.example.androidgroup4.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseFragment
import com.example.androidgroup4.data.model.Profile
import com.example.androidgroup4.data.source.remote.network.ApiResponse
import com.example.androidgroup4.databinding.FragmentProfileBinding
import com.example.androidgroup4.ui.UserViewModel
import com.example.androidgroup4.ui.auth.LoginActivity
import com.example.androidgroup4.ui.main.MainActivity
import com.example.androidgroup4.utils.*
import com.example.androidgroup4.utils.constant.PreferenceKeys
import com.example.androidgroup4.utils.constant.PreferenceKeys.IS_LOGIN
import com.example.androidgroup4.utils.enum.GenderType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val userViewModel: UserViewModel by viewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding =
        FragmentProfileBinding::inflate

    override fun initIntent() {

    }

    override fun initUI() {
        showLoggedInStateView()
    }

    override fun initAction() {
        binding.apply {
            fabChangePhoto.setOnClickListener {
            }

            fabEditProfile.setOnClickListener {
                val profile = Profile(
                    getString(R.string.sample_name),
                    getString(R.string.sample_date_of_birth),
                    GenderType.MALE.type,
                    getString(R.string.sample_id_card_number),
                    getString(R.string.sample_address)
                )
                EditProfileActivity.start(this@ProfileFragment.context, profile)
            }

            btnChangePassword.setOnClickListener {
            }

            btnLogout.setOnClickListener {
                getAppPreferenceEditor(requireContext()).putBoolean(IS_LOGIN, false).commit()
                MainActivity.start(requireContext())
                activity?.finish()
            }

            layoutNotLoggedIn.btnLogin.setOnClickListener {
                LoginActivity.start(requireContext())
            }
        }
    }

    override fun initProcess() {
        getUserData()
    }

    override fun initObservable() {
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

    private fun getUserData() {
        lifecycleScope.launchWhenStarted {
            userViewModel.getUserById(
                getAppSharedPreference(requireContext()).getInt(PreferenceKeys.USER_ID, -1)
            ).collect {
                when (it) {
                    is ApiResponse.Success -> {
                        hideLoading()
                        binding.apply {
                            imgProfileUser.setImageUrl(
                                requireContext(),
                                getString(R.string.sample_image_url),
                                pbProfileUser,
                                R.drawable.img_not_available
                            )
                            tvName.text = it.data?.fullname
                            tvEmail.text = it.data?.email
                            tvDateOfBirth.text = it.data?.tgl_lahir
                            tvIdCard.text = it.data?.no_ktp.toString()
                            tvAddress.text = it.data?.alamat
                        }
                    }
                    is ApiResponse.Failure -> {
                        hideLoading()
                        showToast(requireContext(), it.message)
                    }
                    is ApiResponse.Loading -> {
                        showLoading()
                    }
                }
            }
        }
    }

}