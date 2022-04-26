package com.example.todoapp.requests_server

import android.content.Context
import android.view.View
import com.example.todoapp.R
import com.example.todoapp.models.TaskNew
import com.example.todoapp.models.TaskResponse
import com.example.todoapp.user_interface.adapters.TasksAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditTaskRequests(
    private val token: String,
    private val adapter: TasksAdapter,
    private val action: ActionsListener,
    private val context: Context
) {
    fun getTask() {
        ApiService.retrofit.getTasks("Bearer $token")
            .enqueue(object : Callback<List<TaskResponse>> {
                override fun onResponse(
                    call: Call<List<TaskResponse>>,
                    response: Response<List<TaskResponse>>
                ) {
                    if (response.isSuccessful) {
                        adapter.submitList(response.body()!!)
                        action.onErrorAnswer("Обновление")
                    } else
                        when (response.code()) {
                            400 -> action.onErrorAnswer(context.getString(R.string.not_found_task))
                            401 -> {
                                action.onErrorAnswer(context.getString(R.string.no_token))
                                action.onNoToken()
                            }
                        }
                    action.onAnswer(View.GONE)
                }
                override fun onFailure(call: Call<List<TaskResponse>>, t: Throwable) {
                    action.onErrorAnswer(t.message.toString())
                    action.onAnswer(View.GONE)
                }
            })
    }
    fun deleteTask(id: Int) {
        ApiService.retrofit.deleteTask(id, "Bearer $token").enqueue(
            object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful)
                        getTask()
                    else
                        action.onErrorAnswer(context.getString(R.string.no_task))
                }
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    action.onErrorAnswer(t.message.toString())
                }
            }
        )
    }
    fun putTask(tasknew: TaskNew) {
        action.onAnswer(View.VISIBLE)
        ApiService.retrofit.putTask(tasknew, "Bearer $token").enqueue(
            object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful)
                        getTask()
                    else
                        when (response.code()) {
                            400 -> action.onErrorAnswer(context.getString(R.string.not_text_task))
                            401 -> {
                                action.onErrorAnswer(context.getString(R.string.no_token))
                                action.onNoToken()
                            }
                        }
                }
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    action.onErrorAnswer(t.message.toString())
                }
            }
        )
    }
    fun changeTask(id: Int) {
        ApiService.retrofit.changeTask(id, "Bearer $token").enqueue(
            object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful)
                        getTask()
                    else
                        when (response.code()) {
                            400 -> action.onErrorAnswer(context.getString(R.string.no_task))
                            401 -> {
                                action.onErrorAnswer(context.getString(R.string.no_token))
                                action.onNoToken()
                            }
                        }
                }
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    action.onErrorAnswer(t.message.toString())
                }
            }
        )
    }
    interface ActionsListener {
        fun onErrorAnswer(text: String)
        fun onAnswer(visibility: Int)
        fun onNoToken()
    }
}
