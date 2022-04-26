package com.example.todoapp.models

import com.google.gson.annotations.SerializedName

data class UserCreate(
    @SerializedName("display_name")
    val displayname: String,
    val email: String,
    val password: String
)
