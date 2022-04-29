package com.example.todoapp.user_interface.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentCategoryBinding
import com.example.todoapp.models.TaskNew
import com.example.todoapp.models.TaskResponse
import com.example.todoapp.preferences.PreferencesManager
import com.example.todoapp.requests_server.EditTaskRequests
import com.example.todoapp.user_interface.adapters.TasksAdapter

class CategoryFragment :
    Fragment(),
    TasksAdapter.OnItemClickListener,
    EditTaskRequests.ActionsListener {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var adapter: TasksAdapter
    private lateinit var preferences: PreferencesManager
    companion object {
        private lateinit var requests: EditTaskRequests
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        adapter = TasksAdapter(this)
        binding.recyclelViewCategory.adapter = adapter
        preferences = PreferencesManager(requireContext())
        requests = EditTaskRequests(preferences.token, adapter, this, requireContext())
        binding.floatingActionButtonCategory.setOnClickListener {
            buildDialogPut()
            binding.recyclelViewCategory.layoutManager?.scrollToPosition(adapter.itemCount - 1)
        }
        return binding.root
    }

    private fun buildDialogPut() {
        val input = EditText(requireContext())
        input.hint = getString(R.string.task)
        val dialig = AlertDialog.Builder(requireContext())
            .setTitle(R.string.newtask_title)
            .setView(input)
            .setPositiveButton(R.string.pozition_button_add, null)
            .setNegativeButton(R.string.negativ_button_cancel, null)
            .create()
        dialig.setOnShowListener {
            dialig.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                if (input.text.toString().isNotEmpty()) {
                    requests.putTask(TaskNew(input.text.toString()))
                    dialig.dismiss()
                } else {
                    input.error = getString(R.string.field_emptiness_error)
                }
            }
        }
        dialig.show()
    }

    fun updateTasks() {
        requests.getTask()
    }

    fun buildDialogDelete(taskid: Int) {
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.delete_question)
            .setPositiveButton(R.string.pozitiv_button_yes) { _, _ ->
                requests.deleteTask(taskid)
            }
            .setNegativeButton(R.string.negativ_button_no) { _, _ -> }
            .create()
            .show()
    }

    override fun onItemCliked(task: TaskResponse) {
        requests.changeTask(task.id)
    }

    override fun onItemLongCliked(task: TaskResponse) {
        buildDialogDelete(task.id)
    }

    override fun onErrorResponse(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    }

    override fun onResponse(visibility: Int) {
        binding.progressBarCategoty.visibility = visibility
    }
    override fun onNoToken() {
        activity?.finish()
    }
}
