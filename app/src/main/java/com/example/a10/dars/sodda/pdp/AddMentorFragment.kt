package com.example.a10.dars.sodda.pdp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.a10.dars.sodda.pdp.databinding.FragmentAddMentorBinding
import com.example.a10.dars.sodda.pdp.room.database.AppDatabase
import com.example.a10.dars.sodda.pdp.room.entity.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddMentorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddMentorFragment : Fragment() {
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
    lateinit var mentorList: ArrayList<Mentors>
    lateinit var binding: FragmentAddMentorBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddMentorBinding.inflate(inflater, container, false)
        appDatabase = AppDatabase.getInstance(requireContext())
        mentorList = ArrayList()
        if (appDatabase.pdpDao().getAllMentors().isNotEmpty())
            mentorList.addAll(appDatabase.pdpDao().getAllMentors())
        binding.apply {
            toolBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            var isHas = false
            save.setOnClickListener {
                val name = lastName.text?.trim().toString()
                val secondName = firstName.text?.trim().toString()
                val middleName = middleName.text?.trim().toString()
                val courses = arguments?.getSerializable("course") as com.example.a10.dars.sodda.pdp.room.entity.Courses
                if (secondName.isNotEmpty() && name.isNotEmpty() && middleName.isNotEmpty()) {
                    for (i in 0 until mentorList.size) {
                        if (mentorList[i].secondName == secondName && mentorList[i].name == name && mentorList[i].middleName == middleName) {
                            isHas = true
                            break
                        }
                    }
                    if (!isHas) {
                        var mentorNew = Mentors(name, secondName, middleName, courses.id)
                        appDatabase.pdpDao().insertMentor(mentorNew)
                        mentorList.add(mentorNew)
                        findNavController().popBackStack()
                        Toast.makeText(
                            binding.root.context,
                            "Ma'lumot saqlandi",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            binding.root.context,
                            "Bunday ismli mentor mavjud!!",
                            Toast.LENGTH_SHORT
                        ).show()
                        isHas = false
                    }
                } else {
                    Toast.makeText(
                        binding.root.context,
                        "Iltimos bo'sh maydonlarni to'ldiring",
                        Toast.LENGTH_SHORT
                    ).show()
                }
//                if (name.isNotEmpty() && secondName.isNotEmpty() && middleName.isNotEmpty()) {
//                    val mentors = Mentors(name, secondName, middleName, courses)
//                    myDbHelper.insertMentor(mentors)
//                    Toast.makeText(requireContext(), "Successfully added", Toast.LENGTH_SHORT).show()
//                    findNavController().popBackStack()
//                } else
//                    Toast.makeText(requireContext(), "Fill all fields", Toast.LENGTH_SHORT).show()
//            }
            }
            return binding.root
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddMentorFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddMentorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}