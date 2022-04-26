package com.example.todoapp.models

data class TaskResponse(
    val id: Int,
    val userId: Int,
    val text: String,
    val isCompleted: Boolean
)
