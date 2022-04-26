package com.example.todoapp.requests_server

import com.example.todoapp.models.TaskNew
import com.example.todoapp.models.TaskResponse
import com.example.todoapp.models.Token
import com.example.todoapp.models.UserCreate
import com.example.todoapp.models.UserLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface RequestsApi {
    @POST("user/create")
    fun userCreate(
        @Body usercreate: UserCreate
    ): Call<Token>

    @POST("user/login")
    fun userLogin(
        @Body userlogin: UserLogin
    ): Call<Token>

    @GET("todos")
    fun getTasks(
        @Header("Authorization") token: String
    ): Call<List<TaskResponse>>

    @POST("todos")
    fun putTask(
        @Body text: TaskNew,
        @Header("Authorization") token: String
    ): Call<Unit>

    @PUT("todos/mark")
    fun changeTask(
        @Query("id") id: Int,
        @Header("Authorization") token: String
    ): Call<Unit>

    @DELETE("todos/{id}")
    fun deleteTask(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Call<Unit>
}
