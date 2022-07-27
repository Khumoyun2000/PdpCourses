package com.example.a10.dars.sodda.pdp.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.a10.dars.sodda.pdp.FragmentForCreatingCources
import com.example.a10.dars.sodda.pdp.room.entity.*

class MyViewPagerAdapter(
    private val listOfTitles: ArrayList<String>,
    val courses: Courses,
    fragment: Fragment
) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return listOfTitles.size
    }

    override fun createFragment(position: Int): Fragment {

        return FragmentForCreatingCources.newInstance(courses,listOfTitles[position])
    }

}