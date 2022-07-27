package com.example.a10.dars.sodda.pdp

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.a10.dars.sodda.pdp.databinding.FragmentEditGroupStudentBinding
import com.example.a10.dars.sodda.pdp.room.database.AppDatabase
import com.example.a10.dars.sodda.pdp.room.entity.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditGroupStudentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditGroupStudentFragment : Fragment() {
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
    lateinit var datePicker: DatePickerDialog
    lateinit var binding: FragmentEditGroupStudentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditGroupStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        initialiseVariables()
        setOnclickListeners()

    }

    private fun setOnclickListeners() {
        val student = arguments?.getSerializable("student") as Student
        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
            binding.apply {
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

                        val editedStudent =
                            Student(
                                nameOfStudent,
                                surNameOfStudent,
                                middleNameOfStudent,
                                date,
                                student.courseId,
                                student.groupId,
                                student.mentorId
                            )
                        appDatabase.pdpDao().editStudent(editedStudent)
                        Toast.makeText(requireContext(), "Successfully changed", Toast.LENGTH_SHORT)
                            .show()
                        findNavController().popBackStack()
                    } else {
                        Toast.makeText(requireContext(), "fill all fields", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun initialiseVariables() {
        appDatabase = AppDatabase.getInstance(requireContext())
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditGroupStudentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditGroupStudentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}