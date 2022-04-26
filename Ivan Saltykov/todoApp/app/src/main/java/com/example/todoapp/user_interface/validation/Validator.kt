package com.example.todoapp.user_interface.validation

import android.content.Context
import com.example.todoapp.R

class Validator(private val context: Context) {
    fun checkPassword(userPassword: String): String? {
        return if (userPassword.length < 8)
            context.getString(R.string.error_password)
        else
            null
    }
    fun checkEmail(userEmail: String): String? {
        return if ((userEmail.length < 7) and (!userEmail.contains("@")))
            context.getString(R.string.error_email)
        else
            null
    }
    fun checkConfirmPassword(userPassword: String, userConfirmPassword: String): String? {
        return if (userPassword != userConfirmPassword || userConfirmPassword.isEmpty())
            context.getString(R.string.error_confirmpassword)
        else
            null
    }
    fun checkName(userName: String): String? {
        return if (userName.length <= 4)
            context.getString(R.string.error_name)
        else
            null
    }
}
