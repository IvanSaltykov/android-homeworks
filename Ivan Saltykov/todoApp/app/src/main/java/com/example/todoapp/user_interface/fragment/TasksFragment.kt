package com.example.todoapp.user_interface.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentTasksBinding
import com.example.todoapp.user_interface.adapters.PagerAdapterTasks
import com.google.android.material.tabs.TabLayoutMediator

class TasksFragment : Fragment() {
    lateinit var binding: FragmentTasksBinding
    lateinit var adapterPager: PagerAdapterTasks

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        val fragments =
            mutableListOf(
                CategoryFragment(),
                CategoryFragment(),
                CategoryFragment()
            )
        adapterPager = PagerAdapterTasks(
            requireActivity().supportFragmentManager,
            fragments,
            lifecycle
        )
        binding.viewPagerTasks.adapter = adapterPager
        TabLayoutMediator(binding.tabLayoutTasks, binding.viewPagerTasks) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.category_work)
                1 -> getString(R.string.category_study)
                2 -> getString(R.string.category_entertainments)
                else -> null
            }
        }.attach()
        binding.viewPagerTasks.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    fragments[position].updateTasks()
                }
            }
        )
        return binding.root
    }
}
