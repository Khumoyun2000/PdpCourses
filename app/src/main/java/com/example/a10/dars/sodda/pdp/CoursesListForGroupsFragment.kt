package com.example.a10.dars.sodda.pdp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.a10.dars.sodda.pdp.adapters.RvForCoursesList
import com.example.a10.dars.sodda.pdp.databinding.FragmentCoursesListForGroupsBinding
import com.example.a10.dars.sodda.pdp.room.database.AppDatabase
import com.example.a10.dars.sodda.pdp.room.entity.Courses

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CoursesListForGroupsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CoursesListForGroupsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var appDatabase: AppDatabase
    lateinit var getCoursesList: ArrayList<Courses>
    lateinit var rvForCoursesList: RvForCoursesList
    lateinit var binding: FragmentCoursesListForGroupsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCoursesListForGroupsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initializeVariables()
        setAdapterForRecyclerView()
    }


    private fun initializeVariables() {
        appDatabase = AppDatabase.getInstance(requireContext())
        getCoursesList = ArrayList()
        if (appDatabase.pdpDao().getAllCourses().isNotEmpty()) {
            getCoursesList.addAll(appDatabase.pdpDao().getAllCourses())
        }
        rvForCoursesList = RvForCoursesList(object : RvForCoursesList.OnMyItemClickListener {
            override fun onMyItemClickListener(courses: Courses, position: Int) {
                val bundle = bundleOf("course" to courses)
                findNavController().navigate(R.id.groupsPageFragment, bundle)
            }

        })
    }

    private fun setAdapterForRecyclerView() {
        binding.apply {

            rvForCoursesList.submitList(getCoursesList)
            rvForCoursesList.notifyItemInserted(getCoursesList.size)
            rv.adapter = rvForCoursesList

            toolBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CoursesListForGroupsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CoursesListForGroupsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}