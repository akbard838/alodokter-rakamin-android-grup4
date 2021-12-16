package com.example.androidgroup4.ui.profile

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseFragment
import com.example.androidgroup4.data.model.User
import com.example.androidgroup4.databinding.FragmentProfileBinding
import com.example.androidgroup4.ui.auth.ChangePasswordActivity
import com.example.androidgroup4.ui.auth.LoginActivity
import com.example.androidgroup4.ui.main.MainActivity
import com.example.androidgroup4.ui.success.SuccessActivity
import com.example.androidgroup4.ui.viewmodel.UserViewModel
import com.example.androidgroup4.utils.*
import com.example.androidgroup4.utils.constant.PreferenceKeys.USER_TOKEN
import com.example.androidgroup4.utils.enum.SuccessType
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val userViewModel: UserViewModel by viewModels()

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
            fabChangePhoto.setOnClickListener {

            }

            fabEditProfile.setOnClickListener {
                user?.let {
                    EditProfileActivity.start(this@ProfileFragment.context, it)
                }
            }

            btnChangePassword.setOnClickListener {
                ChangePasswordActivity.start(requireContext())
            }

            btnLogout.setOnClickListener {
                getAppPreferenceEditor(requireContext()).putString(USER_TOKEN, emptyString()).apply()
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
        userViewModel.profile.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    hideLoading()
                    binding.apply {
                            user = it.data?.toUser()
                            imgProfileUser.setImageUrl(
                                requireContext(),
                                getString(R.string.sample_image_url),
                                pbProfileUser,
                                R.drawable.img_not_available
                            )
                            tvName.text = user?.name
                            tvEmail.text = user?.email
                            tvDateOfBirth.text =
                                getBirthDateFormat(user?.birthDate ?: emptyString())
                            tvIdCard.text = user?.idCardNumber
                            tvAddress.text = user?.address
                        }
                }
                is Resource.Error -> {
                    hideLoading()
                    Toast.makeText(requireContext(), it.apiError.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (MainActivity.getUserLoggedInStatus(requireContext())) getUserData()
    }

    override fun onDestroy() {
        hideLoading()
        super.onDestroy()
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

        userViewModel.getUserProfile("test@gmail.com")

//        lifecycleScope.launchWhenStarted {
//            userViewModel.getUserById(
//                getAppSharedPreference(requireContext()).getInt(PreferenceKeys.USER_ID, -1)
//            ).collect {
//                when (it) {
//                    is ApiResponse.Success -> {
//                        hideLoading()
//                        binding.apply {
//                            user = it.data
//                            imgProfileUser.setImageUrl(
//                                requireContext(),
//                                getString(R.string.sample_image_url),
//                                pbProfileUser,
//                                R.drawable.img_not_available
//                            )
//                            tvName.text = it.data?.fullname
//                            tvEmail.text = it.data?.email
//                            tvDateOfBirth.text =
//                                getBirthDateFormat(it.data?.tgl_lahir ?: emptyString())
//                            tvIdCard.text = it.data?.no_ktp.toString()
//                            tvAddress.text = it.data?.alamat
//                        }
//                    }
//                    is ApiResponse.Failure -> {
//                        hideLoading()
//                        showToast(requireContext(), it.message)
//                    }
//                    is ApiResponse.Loading -> {
//                        showLoading()
//                    }
//                }
//            }
//        }
    }

}