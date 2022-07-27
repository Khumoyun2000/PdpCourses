package com.example.a10.dars.sodda.pdp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.a10.dars.sodda.pdp.adapters.StudentRvAdapter
import com.example.a10.dars.sodda.pdp.databinding.FragmentGroupResultBinding
import com.example.a10.dars.sodda.pdp.room.database.AppDatabase
import com.example.a10.dars.sodda.pdp.room.entity.Groups
import com.example.a10.dars.sodda.pdp.room.entity.Student

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GroupResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupResultFragment : Fragment() {
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

    lateinit var allGroups: ArrayList<Groups>
    lateinit var studentList: ArrayList<Student>
    lateinit var appDatabase: AppDatabase
    lateinit var studentRvAdapter: StudentRvAdapter
    lateinit var binding: FragmentGroupResultBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentGroupResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initialiseVariables()
        var group = arguments?.getSerializable("group") as Groups
        val course = appDatabase.pdpDao().getCourseById(group.courseId!!)
        if (appDatabase.pdpDao().getStudentsByGroup(group.courseId!!, group.id!!).isNotEmpty()) {
            studentList.addAll(
                appDatabase.pdpDao().getStudentsByGroup(group.courseId!!, group.id!!)
            )
        }

        studentRvAdapter = StudentRvAdapter(studentList)
        binding.apply {
            toolGroupResult.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            toolGroupResult.title = "${course.courseName}"
            toolGroupResult.inflateMenu(R.menu.menu)
            toolGroupResult.menu.getItem(0)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            toolGroupResult.menu.getItem(0)
                .setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener {
                    override fun onMenuItemClick(item: MenuItem?): Boolean {
                        val bundle = bundleOf("group" to group)
                        findNavController().navigate(R.id.addGroupStudentFragment, bundle)
                        return true
                    }
                })
            studentRv.adapter = studentRvAdapter
            studentRvAdapter.notifyItemInserted(studentList.size)
            studentRvAdapter.setEdit(object : StudentRvAdapter.EditItemOnClickListener {
                override fun editOnClick(student: Student, position: Int) {
                    val bundle = bundleOf("student" to student)
                    findNavController().navigate(R.id.editGroupStudentFragment, bundle)
                }
            })
            studentRvAdapter.setDelete(object : StudentRvAdapter.DeleteItemOnClickListener {
                override fun deleteOnClick(student: Student, position: Int) {
                    appDatabase.pdpDao().deleteStudent(student)
                    studentList.remove(student)
                    studentRvAdapter.notifyItemRemoved(position)
                }

            })
            allGroups.add(appDatabase.pdpDao().getGroupById(group.id!!))
//            for (allGroup in myDbHelper.getAllGroups()) {
//                if (allGroup.groupName==group.groupName)
//                    allGroups.add(allGroup)
//            }
            groupNameTv.text = "Gruh nomi: ${group.groupName}"
            sumGroupResultTv.text = "O'quvchilar soni: ${allGroups[0].sCount} ta"
            groupTime1Tv.text = "Vaqti: ${group.courseTime}"
            lesson.setOnClickListener {
                if (group.sCount != 0) {
                    val isOpen = "Ochilgan kurslar"
                    group.isOpen = isOpen

                    appDatabase.pdpDao().editGroup(group)
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Darsni boshlash uchun guruhga talaba qoshing",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun initialiseVariables() {
        appDatabase = AppDatabase.getInstance(requireContext())
        studentList = ArrayList()
        allGroups = ArrayList()
    }
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.menu, menu)
//    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GroupResultFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GroupResultFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}