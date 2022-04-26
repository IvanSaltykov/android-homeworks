package com.example.todoapp.user_interface.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemTaskBinding
import com.example.todoapp.models.TaskResponse

class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(task: TaskResponse, clickListener: TasksAdapter.OnItemClickListener) = with(binding) {
        textViewItemTaskName.text = task.text
        checkBoxItemTask.isChecked = task.isCompleted
        root.setOnClickListener {
            binding.checkBoxItemTask.isChecked = !binding.checkBoxItemTask.isChecked
            clickListener.onItemCliked(task)
        }
        root.setOnLongClickListener {
            clickListener.onItemLongCliked(task)
            return@setOnLongClickListener true
        }
    }
}
