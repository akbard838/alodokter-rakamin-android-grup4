package com.example.androidgroup4.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidgroup4.data.model.Schedule
import com.example.androidgroup4.databinding.ItemScheduleBinding
import com.example.androidgroup4.ui.doctor.PatientActivity
import com.example.androidgroup4.utils.changeDateFormat

class ScheduleAdapter(private val items: ArrayList<Schedule>) :
    RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        return ScheduleViewHolder(
            ItemScheduleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val data = items[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = items.size

    class ScheduleViewHolder(private val binding: ItemScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Schedule) {
            binding.tvDay.text = data.date.changeDateFormat("dd MMMM yyyy", "EEEE")
            binding.tvDate.text = data.date
            binding.chipHourOne.text = data.hourOne
            binding.chipHourTwo.text = data.hourTwo
            binding.chipHourOne.setOnClickListener {
                PatientActivity.start(itemView.context)
            }
            binding.chipHourTwo.setOnClickListener {
                PatientActivity.start(itemView.context)
            }
        }
    }

}