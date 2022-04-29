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
                        action.onErrorResponse("Обновление")
                    } else
                        when (response.code()) {
                            400 ->
                                action.onErrorResponse(context.getString(R.string.not_found_task))
                            401 -> {
                                action.onErrorResponse(context.getString(R.string.no_token))
                                action.onNoToken()
                            }
                        }
                    action.onResponse(View.GONE)
                }
                override fun onFailure(call: Call<List<TaskResponse>>, t: Throwable) {
                    action.onErrorResponse(t.message.toString())
                    action.onResponse(View.GONE)
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
                        action.onErrorResponse(context.getString(R.string.no_task))
                }
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    action.onErrorResponse(t.message.toString())
                }
            }
        )
    }
    fun putTask(tasknew: TaskNew) {
        action.onResponse(View.VISIBLE)
        ApiService.retrofit.putTask(tasknew, "Bearer $token").enqueue(
            object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful)
                        getTask()
                    else
                        when (response.code()) {
                            400 -> action.onErrorResponse(context.getString(R.string.not_text_task))
                            401 -> {
                                action.onErrorResponse(context.getString(R.string.no_token))
                                action.onNoToken()
                            }
                        }
                }
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    action.onErrorResponse(t.message.toString())
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
                            400 -> action.onErrorResponse(context.getString(R.string.no_task))
                            401 -> {
                                action.onErrorResponse(context.getString(R.string.no_token))
                                action.onNoToken()
                            }
                        }
                }
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    action.onErrorResponse(t.message.toString())
                }
            }
        )
    }
    interface ActionsListener {
        fun onErrorResponse(text: String)
        fun onResponse(visibility: Int)
        fun onNoToken()
    }
}
