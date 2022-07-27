package com.example.a10.dars.sodda.pdp

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.a10.dars.sodda.pdp.databinding.FragmentAddStudentBinding
import com.example.a10.dars.sodda.pdp.db.MyDbHelper
import com.example.a10.dars.sodda.pdp.room.database.AppDatabase
import com.example.a10.dars.sodda.pdp.room.entity.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddStudentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddStudentFragment : Fragment() {
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

    lateinit var studentsList: ArrayList<StudentsAndGroups>
    lateinit var datePicker: DatePickerDialog
    lateinit var binding: FragmentAddStudentBinding
    lateinit var myDbHelper: MyDbHelper
    lateinit var mentorsList: ArrayList<MentorsForCourse>
    lateinit var groupsList: ArrayList<Groups>
    lateinit var mentorsNameList: ArrayList<String>
    lateinit var groupsNameList: ArrayList<String>
    lateinit var appDatabase: AppDatabase
    lateinit var mentorsAdapter: ArrayAdapter<String>
    lateinit var groupsAdapter: ArrayAdapter<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddStudentBinding.inflate(inflater, container, false)


        binding.apply {


        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initializeVariables()
        loadDataToList()
        setAdapters()
        setOnClickMethods()
    }

    private fun setAdapters() {
        val course = arguments?.getSerializable("course") as Courses
        val courseId = course.id
        binding.apply {
            chooseGroup.setAdapter(groupsAdapter)
            var chosenGroup = ""
            containerOfGroups.setEndIconOnClickListener {
                if (groupsNameList.isEmpty()) {
                    Toast.makeText(requireContext(), "you should add group", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            containerOfMentors.setEndIconOnClickListener {
                if (mentorsNameList.isEmpty()) {
                    Toast.makeText(requireContext(), "you should add mentors", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            chooseGroup.setOnItemClickListener { parent, view, position, id ->
                chosenGroup = chooseGroup.text.toString()
            }
            chooseMentors.setAdapter(mentorsAdapter)
            var chosenMentor = ""
            chooseMentors.setOnItemClickListener { parent, view, position, id ->
                chosenMentor = chooseMentors.text.toString()
            }
            studentsList.addAll(appDatabase.pdpDao().getStudentsOfGroup())
            var noHas = false
            saveStudentBtn.setOnClickListener {
                val nameOfStudent = edtName.text?.trim().toString()
                val surNameOfStudent = edtSurname.text?.trim().toString()
                val middleNameOfStudent = edtMiddleName.text?.trim().toString()
                val date = tvDate.text.trim().toString()
                var groupId: Int? = null
                for (groups in groupsList) {
                    if (chosenGroup.trim().toString() == groups.groupName) {
                        groupId = groups.id

                    }
                }

                if (nameOfStudent.isNotEmpty() && surNameOfStudent.isNotEmpty() && middleNameOfStudent.isNotEmpty() && chosenGroup.isNotEmpty() && chosenMentor.isNotEmpty() && date.isNotEmpty()) {
                    for (studentsAndGroups in studentsList) {
                        for (student in studentsAndGroups.students) {
                            if (student.name == nameOfStudent && student.secondName == surNameOfStudent && student.middleName == middleNameOfStudent) {
                                noHas = true
                                break
                            }
                        }
                    }
                    if (!noHas) {

                        var mentorId: Int? = null


                        val mentorName = chosenMentor.trim().split(" ")
                        for (mentorsForCourse in mentorsList) {
                            for (mentor in mentorsForCourse.mentors) {
                                if (mentor.name == mentorName[0])
                                    mentorId = mentor.id
                            }
                        }

                        if (groupId != null) {
                            var group = appDatabase.pdpDao().getGroupById(groupId)
                            var id = group.id
                            group.isOpen="Ochilgan kurslar"
                            group.sCount = group.sCount?.plus(1)
                            var course = group.courseId
                            var mentor = group.mentorId
                            group.id=id

                            appDatabase.pdpDao().editGroup(group)
                            val student =
                                Student(
                                    nameOfStudent,
                                    surNameOfStudent,
                                    middleNameOfStudent,
                                    date,
                                    courseId,
                                    groupId,
                                    mentorId
                                )
                            appDatabase.pdpDao().insertStudent(student)
                            Toast.makeText(
                                requireContext(),
                                "Successfully added",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            findNavController().popBackStack()
                        }

                    } else {
                        Toast.makeText(
                            binding.root.context,
                            "Guruhda bunday o'quvchi mavjud",
                            Toast.LENGTH_SHORT
                        ).show()
                        noHas = false
                    }
                } else {
                    Toast.makeText(requireContext(), "fill all fields", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun setOnClickMethods() {
        binding.apply {
            toolBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            dateLayout.setOnClickListener {
                datePicker = DatePickerDialog(requireContext())
                datePicker.setOnDateSetListener { view, year, month, dayOfMonth ->
                    tvDate.text = "${dayOfMonth}/${month + 1}/${year}"
                }
                datePicker.show()

            }
        }
    }


    private fun initializeVariables() {
        appDatabase = AppDatabase.getInstance(requireContext())
        val course = arguments?.getSerializable("course") as Courses
        mentorsList = ArrayList()
        groupsList = ArrayList()
        studentsList = ArrayList()
        groupsNameList = ArrayList()

        mentorsNameList = ArrayList()
        mentorsAdapter =
            ArrayAdapter(requireContext(), R.layout.drop_down_item, mentorsNameList)
        groupsAdapter =
            ArrayAdapter(requireContext(), R.layout.drop_down_item, groupsNameList)

    }

    private fun loadDataToList() {
        val course = arguments?.getSerializable("course") as Courses
        if (appDatabase.pdpDao().getAllGroups().isNotEmpty()) {
            for (allGroup in appDatabase.pdpDao().getAllGroups()) {
                if (allGroup.courseId == course.id) {
                    groupsList.add(allGroup)
                }
            }
        } else
            Toast.makeText(
                requireContext(),
                "Bu kursda guruhlar hali  mavjud emas",
                Toast.LENGTH_SHORT
            ).show()
        for (groups in groupsList) {
            groupsNameList.add(groups.groupName.toString())
        }
        if (appDatabase.pdpDao().getMentorsOfCourse().isNotEmpty()) {
            for (mentorsForCourse in appDatabase.pdpDao().getMentorsOfCourse()) {
                mentorsList.add(mentorsForCourse)
            }

        }
        for (mentors in mentorsList) {
            for (mentor in mentors.mentors) {
                mentorsNameList.add(" ${mentor.name} ${mentor.secondName} ")
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
         * @return A new instance of fragment AddStudentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddStudentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}