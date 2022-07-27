package com.example.a10.dars.sodda.pdp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a10.dars.sodda.pdp.databinding.ItemGroupsListBinding
import com.example.a10.dars.sodda.pdp.room.entity.*

class RecyclerViewForGroup(var onMyItemClickListener: OnMyItemClickListener) :
    ListAdapter<Groups, RecyclerViewForGroup.Vh>(MyDiffUtil()) {
    class MyDiffUtil : DiffUtil.ItemCallback<Groups>() {
        override fun areItemsTheSame(oldItem: Groups, newItem: Groups): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Groups, newItem: Groups): Boolean {
            return oldItem.equals(newItem)
        }
    }

    inner class Vh(var itemGroupBinding: ItemGroupsListBinding) :
        RecyclerView.ViewHolder(itemGroupBinding.root) {
        fun onBind(group: Groups) {
            itemGroupBinding.apply {
                groupName.text = group.groupName

            }
            itemGroupBinding.groupName.text = group.groupName
            itemGroupBinding.countOfStudents.text = "Talabalar soni ${group.sCount} ta"
            itemGroupBinding.deleteBtn.setOnClickListener {
                onMyItemClickListener.onDeleteClickListener(group, position)
            }
            itemGroupBinding.root.setOnClickListener {
                onMyItemClickListener.onParentClickListener(group, position)
            }
            itemGroupBinding.editBtn.setOnClickListener {
                onMyItemClickListener.onEditClickListener(group, position)
            }
            itemGroupBinding.showBtn.setOnClickListener {
                onMyItemClickListener.onShowClickListener(group, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            itemGroupBinding = ItemGroupsListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val groups = getItem(position)
        holder.onBind(groups)
    }

    interface OnMyItemClickListener {
        fun onParentClickListener(group: Groups, position: Int)
        fun onEditClickListener(group: Groups, position: Int)
        fun onDeleteClickListener(group: Groups, position: Int)
        fun onShowClickListener(group: Groups, position: Int)
    }
}