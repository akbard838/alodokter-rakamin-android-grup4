package com.example.androidgroup4.ui.doctor

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.data.model.Doctor
import com.example.androidgroup4.data.model.Location
import com.example.androidgroup4.data.model.Schedule
import com.example.androidgroup4.databinding.ActivityDoctorListBinding
import com.example.androidgroup4.ui.adapter.DoctorAdapter
import com.example.androidgroup4.utils.gone
import com.example.androidgroup4.utils.showToast
import com.example.androidgroup4.utils.visible
import java.util.*
import java.util.regex.Pattern

class DoctorListActivity : BaseActivity<ActivityDoctorListBinding>(),
    DoctorAdapter.OnDoctorItemListener {

    companion object {
        fun start(context: Context) {
            Intent(context, DoctorListActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

    private lateinit var doctorAdapter: DoctorAdapter

    private var doctors: ArrayList<Doctor> = arrayListOf()

    override val bindingInflater: (LayoutInflater) -> ViewBinding =
        ActivityDoctorListBinding::inflate

    override fun initIntent() {
    }

    override fun initUI() {
        initRecyclerView()
    }

    override fun initAction() {
    }

    override fun initProcess() {
        getDummyDoctors()
        checkIsDataEmpty()

        binding.fabSearch.setOnClickListener {
            getFilteredData()
            checkIsDataEmpty()
        }

        binding.edtSearchDoctor.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.edtSearchDoctor.text.toString().isEmpty()) {
                    reloadData()
                    checkIsDataEmpty()
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    override fun initObservable() {
    }

    override fun onDoctorItemClicked(data: Doctor, position: Int) {
        DoctorDetailActivity.start(this, data)
    }

    private fun initRecyclerView() {
        doctorAdapter = DoctorAdapter(this, doctors, this)

        binding.apply {
            rvDoctor.setHasFixedSize(true)
            rvDoctor.layoutManager = LinearLayoutManager(this@DoctorListActivity)
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

    private fun getDummyDoctors() {
        val names = resources.getStringArray(R.array.list_of_doctor_name)
        val images = resources.getStringArray(R.array.list_of_doctor_image)
        val specialists = resources.getStringArray(R.array.list_of_doctor_specialist)
        val yoe = resources.getIntArray(R.array.list_of_doctor_yoe)

        val hospitalNames = resources.getStringArray(R.array.list_of_hospital_name)
        val hospitalImages = resources.getStringArray(R.array.list_of_hospital_image)

        names.forEachIndexed { i, _ ->
            doctors.add(
                Doctor(
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
    }

    private fun getFilteredData() {
        reloadData()
        val filtered = doctors.filter {
            Pattern.compile(
                Pattern.quote(binding.edtSearchDoctor.text.toString()),
                Pattern.CASE_INSENSITIVE
            ).matcher(it.name).find();
        }
        doctors.clear()
        doctors.addAll(filtered)
        doctorAdapter.notifyDataSetChanged()
    }

    private fun reloadData() {
        doctors.clear()
        getDummyDoctors()
        doctorAdapter.notifyDataSetChanged()
    }

    private fun checkIsDataEmpty() {
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