package com.example.a10.dars.sodda.pdp.adapters

import android.content.ContentResolver
import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.widget.ArrayAdapter
import com.example.a10.dars.sodda.pdp.R
import com.example.a10.dars.sodda.pdp.model.Mentors

class MentorArrayAdapter(context: Context,val list:ArrayList<Mentors>) :ArrayAdapter<Mentors>(context,
    R.layout.drop_down_item,list) {

}