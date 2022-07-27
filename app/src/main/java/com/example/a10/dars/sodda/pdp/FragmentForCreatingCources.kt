package com.example.a10.dars.sodda.pdp

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.a10.dars.sodda.pdp.adapters.RecyclerViewForGroup
import com.example.a10.dars.sodda.pdp.databinding.DialogEditGroupBinding
import com.example.a10.dars.sodda.pdp.databinding.FragmentForCreatingCourcesBinding
import com.example.a10.dars.sodda.pdp.room.database.AppDatabase
import com.example.a10.dars.sodda.pdp.room.entity.Courses
import com.example.a10.dars.sodda.pdp.room.entity.Groups
import com.example.a10.dars.sodda.pdp.room.entity.Mentors

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentForCreatingCources.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentForCreatingCources : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Courses? = null
    private var param2: String? = null

    val TAG = "GroupsList"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Courses?
            param2 = it.getString(ARG_PARAM2)

        }
    }

    lateinit var binding: FragmentForCreatingCourcesBinding
    lateinit var appDatabase: AppDatabase
    lateinit var mentorsList: ArrayList<Mentors>
    lateinit var mentorsNames: ArrayList<String>
    lateinit var groupsList: ArrayList<Groups>
    lateinit var timesList: ArrayList<String>
    lateinit var recyclerViewForGroup: RecyclerViewForGroup
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForCreatingCourcesBinding.inflate(inflater, container, false)

//z        val studentList=myDbHelper.getAllStudents()


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initialiseVariables()
        val course = param1
        val isOpened = param2
        for (allGroup in appDatabase.pdpDao().getAllGroups()) {
            if (allGroup.courseId == course?.id && allGroup.isOpen == isOpened) {
                groupsList.add(allGroup)
            }


        }

        Log.d("eeee", "${course?.courseName}")

        recyclerViewForGroup =
            RecyclerViewForGroup(object : RecyclerViewForGroup.OnMyItemClickListener {
                override fun onParentClickListener(group: Groups, position: Int) {
                    val bundle = bundleOf("group" to group)
                    findNavController().navigate(R.id.groupResultFragment, bundle)
                }

                override fun onEditClickListener(group: Groups, position: Int) {
                    mentorsList = ArrayList()
                    mentorsNames = ArrayList()
                    val dialog = AlertDialog.Builder(requireContext())
                    val customView =
                        DialogEditGroupBinding.inflate(LayoutInflater.from(requireContext()))
                    val create = dialog.create()
                    create.setView(customView.root)
                    customView.apply {
                        if (appDatabase.pdpDao().getMentorsByCourseId(course?.id!!).isNotEmpty()) {
                            mentorsList.addAll(
                                appDatabase.pdpDao().getMentorsByCourseId(course?.id!!)
                            )
                        }
                        for (mentors in mentorsList) {
                            mentorsNames.add(" ${mentors.name} ${mentors.secondName}")
                        }
                        if (mentorsList.isEmpty()) {

                        } else {
                            val mentorAdapter =
                                ArrayAdapter(
                                    requireContext(),
                                    R.layout.drop_down_item,
                                    mentorsNames
                                )
                            chooseMentors.setAdapter(mentorAdapter)
                        }
                        containerOfMentors.setEndIconOnClickListener {
                            if (mentorsNames.isEmpty()) {
                                Toast.makeText(
                                    requireContext(),
                                    "you should add mentors",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                        var chosenMentor: String = ""
                        chooseMentors.setOnItemClickListener { parent, view, position, id ->
                            if (mentorsList.isEmpty()) {
                                Toast.makeText(
                                    requireContext(),
                                    "you should add mentors",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                            chosenMentor = chooseMentors.text.toString()
                        }
                        val timeAdapter =
                            ArrayAdapter(requireContext(), R.layout.drop_down_item, timesList)
                        chooseTime.setAdapter(timeAdapter)
                        var chosenTime: String = ""
                        chooseTime.setOnItemClickListener { parent, view, position, id ->

                            chosenTime = chooseTime.text.toString()
                        }
                        cancel.setOnClickListener {
                            create.dismiss()
                        }
                        edit.setOnClickListener {
                            val name = edtGroupName.text?.trim().toString()
                            if (name.isNotEmpty() && chosenMentor.isNotEmpty() && chosenTime.isNotEmpty()) {
                                var mentor: Mentors? = null
                                val mentorName = chosenMentor.trim().split(" ")
                                for (mentors in mentorsList) {
                                    if (mentors.name == mentorName[0])
                                        mentor = mentors
                                }
//                                val editGroupList
                                for (allGroup in appDatabase.pdpDao().getAllGroups()) {
                                    if (allGroup.groupName == group.groupName) {

                                    }
                                }
                                group.groupName = name
                                group.mentorId = mentor?.id
                                group.courseTime=chosenTime

                                appDatabase.pdpDao().editGroup(group)
                                groupsList.remove(group)
                                groupsList.add(group)
                                recyclerViewForGroup.notifyItemInserted(groupsList.size)
                                recyclerViewForGroup.notifyDataSetChanged()
                                Toast.makeText(
                                    requireContext(),
                                    "Successfully edited",
                                    Toast.LENGTH_SHORT
                                ).show()
                                create.dismiss()
                            } else
                                Toast.makeText(
                                    requireContext(),
                                    "fill all fields",
                                    Toast.LENGTH_SHORT
                                ).show()

                        }

                    }
                    create.show()
                }


                override fun onDeleteClickListener(group: Groups, position: Int) {
//                    val list: ArrayList<Student> = ArrayList()
//                    list.addAll(appDatabase.pdpDao().getAllStudents())
                    appDatabase.pdpDao().deleteGroup(group)
                    appDatabase.pdpDao().deleteAll(course?.id!!, group.id!!)
                    appDatabase.pdpDao().deleteStudentsByGroup(group.id!!)
//                    for (student in list) {
//                        if (student.groupId == group.id) {
//                            list.remove(student)
//                        }
//                    }
//                    for (i in 0 until list.size) {
//                        appDatabase.pdpDao().insertStudent(list[i])
//                    }
//                    appDatabase.pdpDao().deleteGroup(group)
                    groupsList.remove(group)
                    recyclerViewForGroup.notifyItemRemoved(position)
                    recyclerViewForGroup.notifyDataSetChanged()
                }

                override fun onShowClickListener(group: Groups, position: Int) {
                    val bundle = bundleOf("group" to group)
                    findNavController().navigate(R.id.groupResultFragment, bundle)
                }

            })
        if (groupsList.isNotEmpty())
            recyclerViewForGroup.submitList(groupsList)
        recyclerViewForGroup.notifyItemInserted(groupsList.size)
        recyclerViewForGroup.notifyDataSetChanged()
        binding.apply {
            rvGroups.adapter = recyclerViewForGroup
        }
    }

    private fun initialiseVariables() {
        appDatabase = AppDatabase.getInstance(requireContext())
        timesList = arrayListOf("16:30-18:30", "19:00-21:00")
        groupsList = ArrayList()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentForCreatingCources.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Courses, param2: String) =
            FragmentForCreatingCources().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}