package com.example.androidgroup4.ui.doctor

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.base.BaseFragment
import com.example.androidgroup4.data.model.Doctor
import com.example.androidgroup4.databinding.FragmentDoctorListBinding
import com.example.androidgroup4.ui.adapter.DoctorAdapter
import com.example.androidgroup4.ui.auth.LoginActivity
import com.example.androidgroup4.ui.main.MainActivity
import com.example.androidgroup4.ui.viewmodel.DoctorViewModel
import com.example.androidgroup4.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class DoctorListFragment : BaseFragment<FragmentDoctorListBinding>() {

    private lateinit var doctorAdapter: DoctorAdapter

    private var doctors: ArrayList<Doctor> = arrayListOf()

    private val doctorViewModel: DoctorViewModel by viewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding =
        FragmentDoctorListBinding::inflate

    override fun initIntent() {

    }

    override fun initUI() {
        getDoctors()
        showLoggedInStateView()
        initRecyclerView()
    }

    override fun initAction() {
        doctorAdapter.onDoctorItemClicked = { doctor ->
            DoctorDetailActivity.start(requireContext(), doctor)
        }

        binding.apply {
            edtSearchDoctor.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    doctorViewModel.getSearchDoctors(edtSearchDoctor.text.toString())
                    hideSoftKeyboard(requireContext(), binding.edtSearchDoctor)
                    return@OnEditorActionListener true
                }
                false
            })

            fabSearch.setOnClickListener {
                doctorViewModel.getSearchDoctors(edtSearchDoctor.text.toString())
                activity?.window?.setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                )
            }

            edtSearchDoctor.doAfterTextChanged {
                if (binding.edtSearchDoctor.text.toString().isEmpty()) {
                    reloadData()
                }
            }

            layoutNotLoggedIn.btnLogin.setOnClickListener {
                LoginActivity.start(requireContext())
            }
        }
    }

    override fun initProcess() {
    }

    override fun initObservable() {
        doctorViewModel.doctors.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    hideLoading()
                    doctors.addAll(it.data)
                    doctorAdapter.setData(doctors)
                    checkIsDataEmpty(doctors)
                }
                is Resource.Error -> {
                    hideLoading()
                    Toast.makeText(requireContext(), it.apiError.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        doctorViewModel.searchDoctors.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    doctors.clear()
                    doctors.addAll(it.data)
                    doctorAdapter.setData(doctors)
                    checkIsDataEmpty(doctors)
                    hideLoading()
                }
                is Resource.Error -> {
                    hideLoading()
                    showToast(requireContext(), it.apiError.message)
                }
                else -> {}
            }
        }
    }

    override fun showLoading() {
        binding.rvDoctor.gone()
        binding.pbDoctors.visible()
    }

    override fun hideLoading() {
        binding.rvDoctor.visible()
        binding.pbDoctors.gone()
    }

    private fun showLoggedInStateView() {
        binding.apply {
            if (MainActivity.getUserLoggedInStatus(requireContext())) {
                groupDoctorList.visible()
                binding.layoutNotLoggedIn.clParent.gone()
            } else {
                groupDoctorList.gone()
                binding.layoutNotLoggedIn.clParent.visible()
            }
        }
    }

    private fun initRecyclerView() {
        doctorAdapter = DoctorAdapter(requireContext())

        binding.apply {
            rvDoctor.setHasFixedSize(true)
            rvDoctor.layoutManager = LinearLayoutManager(requireContext())
            rvDoctor.adapter = doctorAdapter
        }
    }

    private fun getDoctors() {
        if (MainActivity.getUserLoggedInStatus(requireContext())) doctorViewModel.getDoctors()
    }

    private fun reloadData() {
        doctors.clear()
        doctorAdapter.setData(doctors)
        getDoctors()
    }

    private fun checkIsDataEmpty(doctors: List<Doctor>) {
        binding.apply {
            if (doctors.isEmpty()) {
                rvDoctor.gone()
                layoutEmpty.layoutEmpty.visible()
            } else {
                rvDoctor.visible()
                layoutEmpty.layoutEmpty.gone()
            }
        }
    }
}