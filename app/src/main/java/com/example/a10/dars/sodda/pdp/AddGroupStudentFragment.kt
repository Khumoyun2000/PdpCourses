package com.example.a10.dars.sodda.pdp

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.a10.dars.sodda.pdp.databinding.FragmentAddGroupStudentBinding
import com.example.a10.dars.sodda.pdp.room.database.AppDatabase
import com.example.a10.dars.sodda.pdp.room.entity.Groups
import com.example.a10.dars.sodda.pdp.room.entity.Student

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddGroupStudentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddGroupStudentFragment : Fragment() {
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

    lateinit var datePicker: DatePickerDialog
    lateinit var appDatabase: AppDatabase
    lateinit var binding: FragmentAddGroupStudentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddGroupStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        appDatabase = AppDatabase.getInstance(requireContext())
        var group = arguments?.getSerializable("group") as Groups
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
            saveStudentBtn.setOnClickListener {
                val nameOfStudent = edtName.text?.trim().toString()
                val surNameOfStudent = edtSurname.text?.trim().toString()
                val middleNameOfStudent = edtMiddleName.text?.trim().toString()
                val date = tvDate.text.trim().toString()
                if (nameOfStudent.isNotEmpty() && surNameOfStudent.isNotEmpty() && middleNameOfStudent.isNotEmpty() && date.isNotEmpty()) {
                    var id = group.id
//                    var name = group.groupName
//                    var time = group.courseTime
//                    var day = group.courseDay
                    group.isOpen = "Ochilgan kurslar"
                    group.sCount = group.sCount?.plus(1)
                    var course = group.courseId
                    var mentor = group.mentorId
                    group.id = id
//                    var changedGroup =
//                        Groups( name, time, day, isOpen, sCount, course, mentor)
//                   changedGroup.id=id
                    appDatabase.pdpDao().editGroup(group)
                    val student =
                        Student(
                            nameOfStudent,
                            surNameOfStudent,
                            middleNameOfStudent,
                            date,
                            course,
                            group.id,
                            mentor
                        )
                    appDatabase.pdpDao().insertStudent(student)
                    Toast.makeText(requireContext(), "Successfully added", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(requireContext(), "fill all fields", Toast.LENGTH_SHORT).show()
                }
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
         * @return A new instance of fragment AddGroupStudentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddGroupStudentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}