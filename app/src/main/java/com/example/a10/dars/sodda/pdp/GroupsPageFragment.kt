package com.example.a10.dars.sodda.pdp

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.a10.dars.sodda.pdp.adapters.MyViewPagerAdapter
import com.example.a10.dars.sodda.pdp.databinding.FragmentGroupsPageBinding
import com.example.a10.dars.sodda.pdp.room.database.AppDatabase
import com.example.a10.dars.sodda.pdp.room.entity.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GroupsPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupsPageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    val TAG = "fragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    lateinit var titles: ArrayList<String>
    lateinit var binding: FragmentGroupsPageBinding
    lateinit var myViewPagerAdapter: MyViewPagerAdapter
    lateinit var appDatabase: AppDatabase
    lateinit var list: ArrayList<Groups>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupsPageBinding.inflate(inflater, container, false)
        val course = arguments?.getSerializable("course") as Courses
        setHasOptionsMenu(true)

        binding.apply {
            toolBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            toolBar.title = "${course.courseName}"

        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val courses = arguments?.getSerializable("course") as Courses
        myViewPagerAdapter =
            MyViewPagerAdapter(titles, courses, requireParentFragment())
        binding.viewPager.adapter = myViewPagerAdapter
        myViewPagerAdapter.notifyDataSetChanged()

    }

    override fun onStart() {
        super.onStart()
        val course = arguments?.getSerializable("course") as Courses
        loadDataToList()
        initialiseVariable()
        setAdapters()
    }

    private fun setAdapters() {
        val course = arguments?.getSerializable("course") as Courses
        myViewPagerAdapter = MyViewPagerAdapter(titles, course, requireParentFragment())
        binding.viewPager.adapter = myViewPagerAdapter
//        myViewPagerAdapter.notifyDataSetChanged()
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()


        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab?.position
                if (position == 1) {
                    binding.toolBar.inflateMenu(R.menu.menu)
                    binding.toolBar.menu.getItem(0)
                        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
                    binding.toolBar.menu.getItem(0)
                        .setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener {
                            override fun onMenuItemClick(item: MenuItem?): Boolean {
                                val bundle = bundleOf("course" to course)
                                findNavController().navigate(R.id.addGroupFragment, bundle)
                                return true
                            }

                        })

                } else if (binding.viewPager.currentItem == 0) {
                    binding.toolBar.menu.clear()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })


    }

    private fun initialiseVariable() {
        appDatabase = AppDatabase.getInstance(requireContext())
        list = ArrayList()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    private fun loadDataToList() {
        titles = ArrayList()
        titles.add("Ochilgan kurslar")
        titles.add("Ochilayotgan kurslar")
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GroupsPageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            GroupsPageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}