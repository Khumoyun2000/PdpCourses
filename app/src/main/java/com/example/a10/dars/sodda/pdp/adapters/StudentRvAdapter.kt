package com.example.a10.dars.sodda.pdp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a10.dars.sodda.pdp.databinding.ItemStudentRvBinding
import com.example.a10.dars.sodda.pdp.room.entity.*


class StudentRvAdapter(private var studentList: ArrayList<Student>) :
    RecyclerView.Adapter<StudentRvAdapter.Vh>() {

    var deleteItemOnClickListener: DeleteItemOnClickListener? = null
    fun setDelete(deleteItemOnClickListener: DeleteItemOnClickListener) {
        this.deleteItemOnClickListener = deleteItemOnClickListener
    }

    var editItemOnClickListener: EditItemOnClickListener? = null
    fun setEdit(editItemOnClickListener: EditItemOnClickListener) {
        this.editItemOnClickListener = editItemOnClickListener
    }

    inner class Vh(var itemStudentRvBinding: ItemStudentRvBinding) :
        RecyclerView.ViewHolder(itemStudentRvBinding.root) {
        fun onBind(student: Student, position: Int) {
            itemStudentRvBinding.tvStudentName.text = student.name
            itemStudentRvBinding.tvStudentLastName.text = student.secondName
            itemStudentRvBinding.studentDateTv.text = student.date

            itemStudentRvBinding.editBtn.setOnClickListener {
                editItemOnClickListener!!.editOnClick(student, position)
            }
            itemStudentRvBinding.deleteBtn.setOnClickListener {
                deleteItemOnClickListener!!.deleteOnClick(student, position)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemStudentRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(studentList[position], position)
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    interface DeleteItemOnClickListener {
        fun deleteOnClick(student: Student, position: Int)
    }

    interface EditItemOnClickListener {
        fun editOnClick(student: Student, position: Int)
    }
}