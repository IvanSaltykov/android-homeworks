package com.example.todoapp.user_interface.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemTaskBinding
import com.example.todoapp.models.TaskResponse

class TasksAdapter(
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<TaskViewHolder>() {
    private var items = mutableListOf<TaskResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = items.get(position)
        holder.bind(item, itemClickListener)
    }

    override fun getItemCount() = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(tasks: List<TaskResponse>) {
        items.clear()
        items.addAll(tasks)
        notifyDataSetChanged()
    }
    interface OnItemClickListener {
        fun onItemCliked(task: TaskResponse)
        fun onItemLongCliked(task: TaskResponse)
    }
}
