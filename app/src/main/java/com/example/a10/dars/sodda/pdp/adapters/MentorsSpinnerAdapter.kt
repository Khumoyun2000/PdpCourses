package com.example.a10.dars.sodda.pdp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import com.example.a10.dars.sodda.pdp.databinding.DropDownItemBinding
import com.example.a10.dars.sodda.pdp.model.Mentors

class MentorsSpinnerAdapter(context: Context,val list: ArrayList<Mentors>,val recourse:Int) : BaseAdapter(){
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Mentors {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var mentorViewHolder: MentorViewHolder
        if (convertView == null) {
            val binding =
                DropDownItemBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
            mentorViewHolder = MentorViewHolder(binding)
        } else {
            mentorViewHolder = MentorViewHolder(DropDownItemBinding.bind(convertView))
        }

        return mentorViewHolder.itemView
    }

    inner class MentorViewHolder {
        val itemView: View
        var dropDownBinding: DropDownItemBinding

        constructor(dropDownBinding: DropDownItemBinding) {
            itemView = dropDownBinding.root
            this.dropDownBinding = dropDownBinding
        }
    }
}