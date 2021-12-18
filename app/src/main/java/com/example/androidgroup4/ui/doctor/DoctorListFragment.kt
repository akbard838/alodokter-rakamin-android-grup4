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
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.base.BaseFragment
import com.example.androidgroup4.data.model.Article
import com.example.androidgroup4.data.model.Doctor
import com.example.androidgroup4.databinding.FragmentDoctorListBinding
import com.example.androidgroup4.ui.adapter.ArticleAdapter
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

    private val doctorViewModel: DoctorViewModel by viewModels()

    private lateinit var layoutManager: LinearLayoutManager

    private var doctors: ArrayList<Doctor> = arrayListOf()

    private var isLoading = false

    private var isDefault = true

    private var page = 1

    private var isLast = false

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding =
        FragmentDoctorListBinding::inflate

    override fun initIntent() {

    }

    override fun initUI() {
        showLoggedInStateView()
        initRecyclerView()
        getDoctors()

        binding.rvDoctor.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val total = doctorAdapter.itemCount
                if (!isLoading && !isLast) {
                    if (visibleItemCount + pastVisibleItem >= total) {
                        page++
                        getDoctors()
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    override fun initAction() {
        doctorAdapter.onDoctorItemClicked = { doctor ->
            DoctorDetailActivity.start(requireContext(), doctor.id)
        }

        binding.apply {
            edtSearchDoctor.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getSearchDoctors()
                    hideSoftKeyboard(requireContext(), binding.edtSearchDoctor)
                    return@OnEditorActionListener true
                }
                false
            })

            fabSearch.setOnClickListener {
                getSearchDoctors()
                activity?.window?.setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                )
            }

            edtSearchDoctor.doAfterTextChanged {
                if (binding.edtSearchDoctor.text.toString().isEmpty()) loadDefaultData()
            }

            layoutNotLoggedIn.btnLogin.setOnClickListener {
                LoginActivity.start(requireContext())
            }

            layoutError.btnRetry.setOnClickListener {
                binding.layoutError.layoutError.gone()
                binding.fabSearch.visible()
                getDoctors()
            }
        }
    }

    override fun initProcess() {

    }

    override fun initObservable() {
        doctorViewModel.doctors.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    isLoading = true
                    binding.pbDoctors.visible()
                }
                is Resource.Success -> {
                    doctors.addAll(it.data)
                    doctorAdapter.setData(doctors)
                    checkIsDataEmpty(doctors)

                    if (it.data.isEmpty()) {
                        binding.pbDoctors.gone()
                        isLast = true
                    } else binding.pbDoctors.invisible()

                    isLoading = false
                }
                is Resource.Error -> {
                    isLoading = false
                    binding.pbDoctors.gone()
                    binding.layoutError.layoutError.visible()
                    binding.fabSearch.gone()
                    Toast.makeText(requireContext(), it.apiError.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        doctorViewModel.searchDoctors.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    binding.pbDoctors.visible()
                }
                is Resource.Success -> {
                    binding.pbDoctors.gone()
                    isLast = true
                    doctors.clear()
                    doctors.addAll(it.data)
                    doctorAdapter.setData(doctors)
                    checkIsDataEmpty(doctors)
                }
                is Resource.Error -> {
                    binding.pbDoctors.gone()
                    binding.layoutError.layoutError.visible()
                    binding.fabSearch.gone()
                    showToast(requireContext(), it.apiError.message)
                }
                else -> {}
            }
        }
    }

    private fun getSearchDoctors() {
        if (binding.edtSearchDoctor.text.toString() == emptyString()) loadDefaultData()
        else doctorViewModel.getSearchDoctors(binding.edtSearchDoctor.text.toString())
    }

    private fun loadDefaultData() {
        doctors.clear()
        doctorAdapter.setData(doctors)
        page = 1
        isDefault = true
        isLast = false
        getDoctors()
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
        layoutManager = LinearLayoutManager(requireContext())
        doctorAdapter = DoctorAdapter(requireContext())
        binding.rvDoctor.setHasFixedSize(true)
        binding.rvDoctor.layoutManager = layoutManager
        binding.rvDoctor.adapter = doctorAdapter
    }

    private fun getDoctors() {
        if (MainActivity.getUserLoggedInStatus(requireContext())) doctorViewModel.getDoctors(page)
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