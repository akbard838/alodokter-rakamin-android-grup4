package com.example.androidgroup4.ui.doctor

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseFragment
import com.example.androidgroup4.data.model.Doctor
import com.example.androidgroup4.data.model.Location
import com.example.androidgroup4.data.model.Schedule
import com.example.androidgroup4.databinding.FragmentDoctorListBinding
import com.example.androidgroup4.ui.adapter.DoctorAdapter
import com.example.androidgroup4.ui.auth.LoginActivity
import com.example.androidgroup4.ui.main.MainActivity
import com.example.androidgroup4.utils.gone
import com.example.androidgroup4.utils.hideSoftKeyboard
import com.example.androidgroup4.utils.visible
import java.util.*
import java.util.regex.Pattern

class DoctorListFragment : BaseFragment<FragmentDoctorListBinding>() {

    private lateinit var doctorAdapter: DoctorAdapter

    private var doctors: ArrayList<Doctor> = arrayListOf()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding
        = FragmentDoctorListBinding::inflate

    override fun initIntent() {

    }

    override fun initUI() {
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
                    doctorAdapter.setData(getFilteredData())
                    checkIsDataEmpty(getFilteredData())
                    hideSoftKeyboard(requireContext(), binding.edtSearchDoctor)
                    return@OnEditorActionListener true
                }
                false
            })

            fabSearch.setOnClickListener {
                doctorAdapter.setData(getFilteredData())
                checkIsDataEmpty(getFilteredData())
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
        doctors.addAll(getDummyDoctors())
        doctorAdapter.setData(doctors)
        checkIsDataEmpty(doctors)
    }

    override fun initObservable() {

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

    private fun getDummySchedules(): ArrayList<Schedule> {
        val schedules = arrayListOf<Schedule>()
        val dates = resources.getStringArray(R.array.list_of_date)

        dates.forEachIndexed { i, _ ->
            schedules.add(
                Schedule(
                    dates[i],
                    getString(R.string.sample_hour),
                    getString(R.string.sample_hour)
                )
            )
        }

        return schedules
    }

    private fun getDummyDoctors(): ArrayList<Doctor>{
        val doctors = arrayListOf<Doctor>()

        val names = resources.getStringArray(R.array.list_of_doctor_name)
        val images = resources.getStringArray(R.array.list_of_doctor_image)
        val specialists = resources.getStringArray(R.array.list_of_doctor_specialist)
        val yoe = resources.getIntArray(R.array.list_of_doctor_yoe)

        val hospitalNames = resources.getStringArray(R.array.list_of_hospital_name)
        val hospitalImages = resources.getStringArray(R.array.list_of_hospital_image)

        names.forEachIndexed { i, _ ->
            doctors.add(
                Doctor(
                    id = id,
                    imageUrl = images[i],
                    name = names[i],
                    specialist = specialists[i],
                    yoe = yoe[i],
                    profile = getString(R.string.sample_lorem_description),
                    location = Location(
                        imageUrl = hospitalImages[i],
                        name = hospitalNames[i],
                        address = getString(R.string.sample_address),
                        dummyDistance = getString(R.string.sample_distance),
                    ),
                    schedules = getDummySchedules()
                )
            )
        }

        return doctors
    }

    private fun getFilteredData(): List<Doctor> {
        val filtered = getDummyDoctors().filter {
            Pattern.compile(
                Pattern.quote(binding.edtSearchDoctor.text.toString()),
                Pattern.CASE_INSENSITIVE
            ).matcher(it.name).find()
        }

        return filtered
    }

    private fun reloadData() {
        doctorAdapter.setData(getDummyDoctors())
        checkIsDataEmpty(getDummyDoctors())
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