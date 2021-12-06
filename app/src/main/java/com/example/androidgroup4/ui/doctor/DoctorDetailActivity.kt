package com.example.androidgroup4.ui.doctor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.data.model.Doctor
import com.example.androidgroup4.data.model.Schedule
import com.example.androidgroup4.databinding.ActivityDoctorDetailBinding
import com.example.androidgroup4.ui.adapter.ScheduleAdapter
import com.example.androidgroup4.utils.setImageUrl

class DoctorDetailActivity : BaseActivity<ActivityDoctorDetailBinding>() {

    companion object {
        fun start(context: Context, doctor: Doctor) {
            Intent(context, DoctorDetailActivity::class.java).apply {
                putExtra("DOCTOR", doctor)
                context.startActivity(this)
            }
        }
    }

    private var doctor: Doctor? = null

    private lateinit var scheduleAdapter: ScheduleAdapter

    private var schedules: ArrayList<Schedule> = arrayListOf()

    override val bindingInflater: (LayoutInflater) -> ViewBinding =
        ActivityDoctorDetailBinding::inflate

    override fun initIntent() {
        doctor = intent.getParcelableExtra("DOCTOR")
    }

    override fun initUI() {
        setupToolbar(binding.toolbarDetail.toolbar, true, getString(R.string.title_doctor_profile))

        initRecyclerView()
    }

    override fun initAction() {

    }

    override fun initProcess() {
        initDummyDoctor()
//        getDummySchedules()
    }

    override fun initObservable() {
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

    private fun initDummyDoctor() {
//        binding.apply {
//            imgDoctor.setImageResource(R.drawable.img_hospital)
//            tvDoctorName.text = getString(R.string.sample_doctor_name)
//            tvDoctorSpecialist.text = getString(R.string.sample_doctor_specialist)
//            tvDistance.text = getString(R.string.sample_distance)
//
//            imgHospital.setImageResource(R.drawable.img_hospital)
//            tvHospitalName.text = getString(R.string.sample_hospital_name)
//            tvHospitalLocation.text = getString(R.string.sample_hospital_location)
//
//            tvProfile.text = getString(R.string.sample_lorem_description)
//        }

        binding.apply {
            doctor?.let { doctor ->
                imgDoctor.setImageUrl(this@DoctorDetailActivity, doctor.imageUrl, pbDoctor, R.drawable.img_not_available )
                tvDoctorName.text = doctor.name
                tvDoctorSpecialist.text = doctor.specialist
                tvDistance.text = doctor.location.dummyDistance

                imgHospital.setImageUrl(this@DoctorDetailActivity, doctor.location.imageUrl, pbHospital, R.drawable.img_not_available)
                tvHospitalName.text = doctor.location.name
                tvHospitalLocation.text = doctor.location.address

                tvProfile.text = doctor.profile

                doctor.schedules.forEach {
                    schedules.add(it)
                }
            }
        }
    }

    private fun getDummySchedules() {
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
    }

}