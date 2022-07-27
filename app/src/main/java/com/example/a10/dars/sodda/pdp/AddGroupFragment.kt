package com.example.a10.dars.sodda.pdp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.a10.dars.sodda.pdp.databinding.FragmentAddGroupBinding
import com.example.a10.dars.sodda.pdp.room.database.AppDatabase
import com.example.a10.dars.sodda.pdp.room.entity.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddGroupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddGroupFragment : Fragment() {
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

    lateinit var groupsList: ArrayList<Groups>
    lateinit var appDatabase: AppDatabase
    lateinit var binding: FragmentAddGroupBinding
    lateinit var mentorsList: ArrayList<MentorsForCourse>
    lateinit var mentorsNames: ArrayList<String>
    lateinit var daysList: ArrayList<String>
    lateinit var timesList: ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadData()
        binding = FragmentAddGroupBinding.inflate(inflater, container, false)





        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initializeVariables()
        val course = arguments?.getSerializable("course") as Courses
        val courseId = course.id
        if (appDatabase.pdpDao().getAllGroups().isNotEmpty()) {
            for (allGroup in appDatabase.pdpDao().getAllGroups()) {
                if (allGroup.courseId == course.id) {
                    groupsList.add(allGroup)
                }
            }
        }
        if (appDatabase.pdpDao().getMentorsOfCourse().isNotEmpty()) {
            for (mentorsForCourse in appDatabase.pdpDao().getMentorsOfCourse()) {
                mentorsList.add(mentorsForCourse)
            }

        }
        for (mentors in mentorsList) {
            for (mentor in mentors.mentors) {
                mentorsNames.add(" ${mentor.name} ${mentor.secondName} ")
            }

        }
        binding.apply {
            toolBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            if (mentorsList.isEmpty()) {

            } else {
                val mentorAdapter =
                    ArrayAdapter(requireContext(), R.layout.drop_down_item, mentorsNames)
                chooseMentors.setAdapter(mentorAdapter)
            }
            containerOfMentors.setOnClickListener {
                if (mentorsList.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "you should add mentors",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

            containerOfMentors.setEndIconOnClickListener {
                if (mentorsNames.isEmpty()) {
                    Toast.makeText(requireContext(), "you should add mentors", Toast.LENGTH_SHORT)
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
            val timeAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, timesList)
            chooseTime.setAdapter(timeAdapter)
            var chosenTime: String = ""
            chooseTime.setOnItemClickListener { parent, view, position, id ->

                chosenTime = chooseTime.text.toString()
            }
            val dateAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, daysList)
            var chosenDate = ""
            chooseDay.setAdapter(dateAdapter)
            chooseDay.setOnItemClickListener { parent, view, position, id ->
                chosenDate = chooseDay.text.toString()
            }
            var isHas = false
            saveGroupBtn.setOnClickListener {
                val groupName = edtGroupName.text?.trim().toString()
                var isOpen = "Ochilayotgan kurslar"
                var count = 0
                if (groupName.isNotEmpty() && chosenMentor.isNotEmpty() && chosenTime.isNotEmpty() && chosenDate.isNotEmpty() && isOpen.isNotEmpty()) {
                    for (i in 0 until groupsList.size) {
                        if (groupsList[i].groupName == groupName) {
                            isHas = true
                            break
                        }
                    }
                    if (!isHas) {
                        var mentorId: Int? = null
                        val mentorName = chosenMentor.trim().split(" ")
                        for (mentors in mentorsList) {
                            for (mentor in mentors.mentors) {
                                if (mentor.name == mentorName[0])
                                    mentorId = mentor.id
                            }

                        }
                        val group =
                            Groups(groupName, chosenTime, chosenDate, isOpen, count, courseId,mentorId)
                        appDatabase.pdpDao().insertGroup(group)
                        groupsList.add(group)
                        Log.d("siz", "${groupsList.size}")
                        Toast.makeText(binding.root.context, "Saqlandi", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    } else {
                        Toast.makeText(
                            binding.root.context,
                            "Bunday $groupName guruh mavjud boshqa nom bering guruhga!!!",
                            Toast.LENGTH_SHORT
                        ).show()
                        isHas = false

                    }
                } else
                    Toast.makeText(requireContext(), "fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initializeVariables() {
        appDatabase = AppDatabase.getInstance(requireContext())
        groupsList = ArrayList()
        mentorsList = ArrayList()
        mentorsNames = ArrayList()
    }

    private fun loadData() {
        daysList = arrayListOf("toq kunlar", "juft kunlar")
        timesList = arrayListOf("16:30-18:30", "19:00-21:00")
    }


    override fun onResume() {
        super.onResume()
        val course = arguments?.getSerializable("course") as Courses
        for (allGroup in appDatabase.pdpDao().getAllGroups()) {
            if (allGroup.courseId == course.id) {
                groupsList.add(allGroup)
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
         * @return A new instance of fragment AddGroupFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddGroupFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}