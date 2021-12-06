package com.example.androidgroup4.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidgroup4.R
import com.example.androidgroup4.data.model.Doctor
import com.example.androidgroup4.databinding.ItemDoctorBinding
import com.example.androidgroup4.utils.setImageUrl

class DoctorAdapter(
    private val context: Context,
    private val items: ArrayList<Doctor>,
    private val listener: OnDoctorItemListener
) :
    RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        return DoctorViewHolder(
            ItemDoctorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val data = items[position]
        holder.bind(data, context, listener)
    }

    override fun getItemCount(): Int = items.size

    class DoctorViewHolder(private val binding: ItemDoctorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Doctor, context: Context, listener: OnDoctorItemListener) {
            binding.apply {
                tvName.text = data.name
                tvDistance.text = data.location.dummyDistance
                tvYoe.text = String.format(context.getString(R.string.format_yoe), data.yoe)
                tvSpecialist.text = data.specialist
                imgDoctor.setImageUrl(
                    context,
                    data.imageUrl,
                    pbDoctor,
                    R.drawable.img_not_available
                )
                root.setOnClickListener {
                    listener.onDoctorItemClicked(data, adapterPosition)
                }
            }
        }
    }

    interface OnDoctorItemListener {
        fun onDoctorItemClicked(data: Doctor, position: Int)
    }

}