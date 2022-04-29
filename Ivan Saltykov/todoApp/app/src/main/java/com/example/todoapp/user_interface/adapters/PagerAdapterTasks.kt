package com.example.todoapp.user_interface.adapters

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.todoapp.user_interface.fragment.CategoryFragment

class PagerAdapterTasks(
    fragmentManager: FragmentManager,
    var fragments: MutableList<CategoryFragment>,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount() = 3
    override fun createFragment(position: Int) = fragments[position]
}
