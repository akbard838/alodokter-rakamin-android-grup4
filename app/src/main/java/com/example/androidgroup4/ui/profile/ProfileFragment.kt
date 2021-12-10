package com.example.androidgroup4.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseFragment
import com.example.androidgroup4.data.source.remote.network.ApiResponse
import com.example.androidgroup4.data.source.remote.response.UserResponse
import com.example.androidgroup4.databinding.FragmentProfileBinding
import com.example.androidgroup4.ui.UserViewModel
import com.example.androidgroup4.ui.auth.LoginActivity
import com.example.androidgroup4.ui.main.MainActivity
import com.example.androidgroup4.utils.*
import com.example.androidgroup4.utils.constant.PreferenceKeys
import com.example.androidgroup4.utils.constant.PreferenceKeys.USER_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val userViewModel: UserViewModel by viewModels()

    private var user: UserResponse? = null

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
                user?.let {
                    EditProfileActivity.start(this@ProfileFragment.context, it)
                }
            }

            btnChangePassword.setOnClickListener {

            }

            btnLogout.setOnClickListener {
                getAppPreferenceEditor(requireContext()).putInt(USER_ID, -1).apply()
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
        if (MainActivity.getUserLoggedInStatus(requireContext())) getUserData()
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
                            user = it.data
                            imgProfileUser.setImageUrl(
                                requireContext(),
                                getString(R.string.sample_image_url),
                                pbProfileUser,
                                R.drawable.img_not_available
                            )
                            tvName.text = it.data?.fullname
                            tvEmail.text = it.data?.email
                            tvDateOfBirth.text =
                                getBirthDateFormat(it.data?.tgl_lahir ?: emptyString())
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