package com.example.a10.dars.sodda.pdp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a10.dars.sodda.pdp.databinding.ListCoursesBinding
import com.example.a10.dars.sodda.pdp.room.entity.Courses

class RvForCoursesList(var onMyItemClickListener: OnMyItemClickListener) :
    ListAdapter<Courses, RvForCoursesList.Vh>(MyDiffUtil()) {
    class MyDiffUtil : DiffUtil.ItemCallback<Courses>() {
        override fun areItemsTheSame(oldItem: Courses, newItem: Courses): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Courses, newItem: Courses): Boolean {
            return oldItem.equals(newItem)
        }

    }

    inner class Vh(var listCoursesBinding: ListCoursesBinding) :
        RecyclerView.ViewHolder(listCoursesBinding.root) {
        fun onBind(courses: Courses) {
            listCoursesBinding.text.text = courses.courseName
            listCoursesBinding.root.setOnClickListener {
                onMyItemClickListener.onMyItemClickListener(courses, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            listCoursesBinding = ListCoursesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val courses = getItem(position)
        holder.onBind(courses)
    }

    interface OnMyItemClickListener {
        fun onMyItemClickListener(courses: Courses, position: Int)
    }

}