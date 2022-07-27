package com.example.a10.dars.sodda.pdp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a10.dars.sodda.pdp.databinding.ItemMentorsListBinding
import com.example.a10.dars.sodda.pdp.room.entity.Mentors

class MentorsRVAdapter(var onMyItemClickListener: OnMyItemClickListener) :
    ListAdapter<Mentors, MentorsRVAdapter.VH>(MyDiffUtil()) {
    class MyDiffUtil : DiffUtil.ItemCallback<Mentors>() {
        override fun areItemsTheSame(oldItem: Mentors, newItem: Mentors): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Mentors, newItem: Mentors): Boolean {
            return oldItem.equals(newItem)
        }


    }
    inner class VH(var itemMentorsListBinding: ItemMentorsListBinding) :
        RecyclerView.ViewHolder(itemMentorsListBinding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(mentors: Mentors) {
            itemMentorsListBinding.firstAndLastName.text = "${mentors.secondName} ${mentors.name}"
            itemMentorsListBinding.middleName.text = mentors.middleName
            itemMentorsListBinding.editBtn.setOnClickListener {
                onMyItemClickListener.onEditItemClickListener(mentors, position)
            }
            itemMentorsListBinding.deleteBtn.setOnClickListener {
                onMyItemClickListener.onDeleteItemClickListener(mentors, position)
            }

        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            itemMentorsListBinding = ItemMentorsListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val mentors = getItem(position)
        holder.onBind(mentors)
    }

    interface OnMyItemClickListener {
        fun onMyItemClickListener(mentors: Mentors, position: Int)
        fun onEditItemClickListener(mentors: Mentors, position: Int)
        fun onDeleteItemClickListener(mentors: Mentors, position: Int)
    }

}