package com.example.androidgroup4.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidgroup4.R
import com.example.androidgroup4.data.model.Doctor
import com.example.androidgroup4.databinding.ItemDoctorBinding
import com.example.androidgroup4.utils.setImageUrl

class DoctorAdapter(private val context: Context) :
    RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    private var listData = ArrayList<Doctor>()
    var onDoctorItemClicked: ((Doctor) -> Unit)? = null

    fun setData(newListData: List<Doctor>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

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
        val data = listData[position]
        holder.bind(data, context)
    }

    override fun getItemCount(): Int = listData.size

    inner class DoctorViewHolder(private val binding: ItemDoctorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Doctor, context: Context) {
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
            }
        }

        init {
            binding.root.setOnClickListener {
                onDoctorItemClicked?.invoke(listData[adapterPosition])
            }
        }
    }

}