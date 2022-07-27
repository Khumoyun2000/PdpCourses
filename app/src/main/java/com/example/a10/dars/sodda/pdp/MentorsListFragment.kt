package com.example.a10.dars.sodda.pdp

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.a10.dars.sodda.pdp.adapters.MentorsRVAdapter
import com.example.a10.dars.sodda.pdp.databinding.DialogEditMentorBinding
import com.example.a10.dars.sodda.pdp.databinding.FragmentMentorsListBinding
import com.example.a10.dars.sodda.pdp.room.database.AppDatabase
import com.example.a10.dars.sodda.pdp.room.entity.Courses
import com.example.a10.dars.sodda.pdp.room.entity.Mentors

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MentorsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MentorsListFragment : Fragment() {
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

    var course: Courses? = null
    lateinit var appDatabase: AppDatabase
    lateinit var mentorsRVAdapter: MentorsRVAdapter
    lateinit var getMentorsList: ArrayList<Mentors>
    lateinit var binding: FragmentMentorsListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMentorsListBinding.inflate(inflater, container, false)
        course = arguments?.getSerializable("course") as Courses
        binding.apply {
            toolBar.title = course?.courseName
            toolBar.inflateMenu(R.menu.menu)

            val bundle = bundleOf("course" to course)
            toolBar.menu?.get(0)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            toolBar.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    findNavController().navigate(R.id.addMentorFragment, bundle)
                    return true
                }
            })
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initializeVariables()
        getAllMentorsToList()
        setOnClickMethods()
        recyclerViewItemClickListener()
    }

    fun recyclerViewItemClickListener(): MentorsRVAdapter.OnMyItemClickListener {
        val onClickListener = object : MentorsRVAdapter.OnMyItemClickListener {
            override fun onMyItemClickListener(mentors: Mentors, position: Int) {

            }

            override fun onEditItemClickListener(mentors: Mentors, position: Int) {
                val dialog = AlertDialog.Builder(requireContext())
                val customView =
                    DialogEditMentorBinding.inflate(LayoutInflater.from(requireContext()))
                val create = dialog.create()
                create.setView(customView.root)
                customView.apply {
                    cancel.setOnClickListener {
                        create.dismiss()
                    }
                    edit.setOnClickListener {
                        val name = lastName.text?.trim().toString()
                        val lastName = firstName.text?.trim().toString()
                        val middleName = middleName.text?.trim().toString()
                        if (name.isNotEmpty() && lastName.isNotEmpty() && middleName.isNotEmpty()) {
                            var id = getMentorsList[position].id
                            val mentors = Mentors(name, lastName, middleName, course?.id)
                            getMentorsList.removeAt(position)
                            getMentorsList.add(mentors)
                            appDatabase.pdpDao().editMentors(mentors)
                            Toast.makeText(
                                requireContext(),
                                "Successfully edited",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            mentorsRVAdapter.notifyDataSetChanged()
                            create.dismiss()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Fill all fields",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                    }
                }
                create.show()
            }

            override fun onDeleteItemClickListener(mentors: Mentors, position: Int) {
                getMentorsList.remove(mentors)
                appDatabase.pdpDao().deleteMentor(mentors)
                mentorsRVAdapter.notifyItemRemoved(position)
                mentorsRVAdapter.notifyDataSetChanged()
            }


        }
        return onClickListener
    }

    fun setOnClickMethods() {
        binding.apply {

            toolBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            rv.adapter = mentorsRVAdapter
        }
    }

    private fun getAllMentorsToList() {
        if (appDatabase.pdpDao().getMentorsByCourseId(course?.id!!).isNotEmpty()) {

                  getMentorsList.addAll(appDatabase.pdpDao().getMentorsByCourseId(course?.id!!))

            }



    }

    fun initializeVariables() {
        appDatabase = AppDatabase.getInstance(requireContext())
        getMentorsList = ArrayList()
        mentorsRVAdapter = MentorsRVAdapter(
            recyclerViewItemClickListener()
        )
        mentorsRVAdapter.submitList(getMentorsList)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MentorsListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MentorsListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}