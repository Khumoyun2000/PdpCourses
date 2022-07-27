package com.example.a10.dars.sodda.pdp

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.a10.dars.sodda.pdp.adapters.RvForCoursesList
import com.example.a10.dars.sodda.pdp.databinding.DialogAddCourseBinding
import com.example.a10.dars.sodda.pdp.databinding.FragmentCoursesListBinding
import com.example.a10.dars.sodda.pdp.room.database.AppDatabase
import com.example.a10.dars.sodda.pdp.room.entity.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CoursesListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CoursesListFragment : Fragment() {
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

    lateinit var binding: FragmentCoursesListBinding
    lateinit var appDatabase: AppDatabase
    lateinit var coursesList: ArrayList<com.example.a10.dars.sodda.pdp.room.entity.Courses>
    lateinit var rvForCoursesList: RvForCoursesList
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCoursesListBinding.inflate(inflater, container, false)
        appDatabase = AppDatabase.getInstance(requireContext())
        rvForCoursesList = RvForCoursesList(object : RvForCoursesList.OnMyItemClickListener {
            override fun onMyItemClickListener(courses: Courses, position: Int) {
                val bundle = bundleOf("course" to courses)
                findNavController().navigate(R.id.courseDetailsFragment, bundle)
            }

        })
        coursesList = ArrayList()
        if (appDatabase.pdpDao().getAllCourses().isNotEmpty()) {
            coursesList.addAll(appDatabase.pdpDao().getAllCourses())
        }
        binding.apply {
            var isHas = false
            Log.d("aaa", "${coursesList.size}")
            rvForCoursesList.submitList(coursesList)
            rv.adapter = rvForCoursesList

            toolBar.inflateMenu(R.menu.menu)
            toolBar.menu?.get(0)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            toolBar.setOnMenuItemClickListener { item ->
                val id = item?.itemId
                when (id) {
                    R.id.menu_add -> {
                        val customView =
                            DialogAddCourseBinding.inflate(LayoutInflater.from(requireContext()))
                        val dialog = AlertDialog.Builder(requireContext())
                        val create = dialog.create()
                        create.setView(customView.root)
//                                                    create.window?.setBackgroundDrawable(
//                                                        ColorDrawable(Color.TRANSPARENT)
//                                                    )
                        customView.apply {
                            close.setOnClickListener {
                                create.dismiss()
                            }
                            add.setOnClickListener {
                                val name = courseName.text?.trim().toString()
                                val description = courseDesc.text?.trim().toString()
                                if (name.isNotEmpty() && description.isNotEmpty()) {
                                    for (i in 0 until coursesList.size) {
                                        if (coursesList[i].courseName == name) {
                                            isHas = true
                                            break
                                        }
                                    }
                                    if (!isHas) {

                                        var course = Courses(
                                                name,
                                                description
                                            )
                                        coursesList.add(course)
                                        rvForCoursesList.submitList(coursesList)
                                        rvForCoursesList.notifyItemChanged(coursesList.size)
                                       appDatabase.pdpDao().insertCourse(course)
                                        create.dismiss()
                                        Toast.makeText(
                                            binding.root.context,
                                            "Saqlandi",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            binding.root.context,
                                            "Bunday kurs mavjud boshqa kusr nomini kiriting???",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        isHas = false
                                    }
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "fill all fields",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }


                            }
                        }
                        create.show()
                    }
                }
                true
            }
            toolBar.setNavigationOnClickListener {
                findNavController().popBackStack()

            }

        }

        return binding.root
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CoursesListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CoursesListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}