package com.example.androidgroup4.ui.doctor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.data.model.Doctor
import com.example.androidgroup4.data.model.Schedule
import com.example.androidgroup4.databinding.ActivityDoctorDetailBinding
import com.example.androidgroup4.ui.adapter.ScheduleAdapter
import com.example.androidgroup4.ui.viewmodel.DoctorViewModel
import com.example.androidgroup4.utils.*
import com.example.androidgroup4.utils.constant.BundleKeys
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorDetailActivity : BaseActivity<ActivityDoctorDetailBinding>() {

    companion object {
        fun start(context: Context, doctor: Doctor) {
            Intent(context, DoctorDetailActivity::class.java).apply {
                putExtra(BundleKeys.DOCTOR, doctor)
                context.startActivity(this)
            }
        }
    }

    private val doctorViewModel: DoctorViewModel by viewModels()

    private var doctor: Doctor? = null

    private lateinit var scheduleAdapter: ScheduleAdapter

    private var schedules: ArrayList<Schedule> = arrayListOf()

    override val bindingInflater: (LayoutInflater) -> ViewBinding =
        ActivityDoctorDetailBinding::inflate

    override fun initIntent() {
        doctor = intent.getParcelableExtra(BundleKeys.DOCTOR)
    }

    override fun initUI() {
        setupToolbar(binding.toolbarDetail.toolbar, true, getString(R.string.title_doctor_profile))

        doctor?.let {
            doctorViewModel.getDetailDoctor(it.id)
        }
    }

    override fun initAction() = Unit

    override fun initProcess() = Unit

    override fun initObservable() {
        doctorViewModel.detailDoctor.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    binding.pbDoctor.visible()
                    binding.pbHospital.visible()
                }
                is Resource.Success -> {
                    binding.pbDoctor.gone()
                    binding.pbHospital.gone()

                    it.data?.let { doctor ->
                        with(binding) {
                            imgDoctor.setImageUrl(this@DoctorDetailActivity, doctor.imageUrl, pbDoctor, R.drawable.img_not_available )
                            imgHospital.setImageUrl(this@DoctorDetailActivity, doctor.location.imageUrl, pbHospital, R.drawable.img_not_available)
                            tvDoctorName.text = doctor.name
                            tvDoctorSpecialist.text = doctor.specialist
                            tvDistance.text = doctor.location.dummyDistance
                            tvHospitalName.text = doctor.location.name
                            tvHospitalLocation.text = doctor.location.address
                            tvProfile.text = doctor.profile

                            doctor.schedules.forEach { schedule ->
                                schedules.add(schedule)
                            }
                            initRecyclerView()
                        }
                    }
                }
                is Resource.Error -> {
                    binding.pbDoctor.gone()
                    binding.pbHospital.gone()
                    showToast(this, it.apiError.message)
                }
                else -> {}
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }

    private fun initRecyclerView() {
        scheduleAdapter = ScheduleAdapter(schedules)

        binding.apply {
            rvSchedule.setHasFixedSize(true)
            rvSchedule.layoutManager = LinearLayoutManager(
                this@DoctorDetailActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            rvSchedule.adapter = scheduleAdapter
        }
    }

}