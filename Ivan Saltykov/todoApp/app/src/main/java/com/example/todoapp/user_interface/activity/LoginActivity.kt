package com.example.todoapp.user_interface.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.R
import com.example.todoapp.databinding.ActivityLoginBinding
import com.example.todoapp.models.Token
import com.example.todoapp.models.UserLogin
import com.example.todoapp.preferences.PreferencesManager
import com.example.todoapp.requests_server.ApiService
import com.example.todoapp.user_interface.validation.Validator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var preferences: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val validator = Validator(this)
        preferences = PreferencesManager(this)
        if (!preferences.token.isEmpty())
            signIn(preferences.email)
        binding.buttonLoginSingIn.setOnClickListener {
            val userPassword = binding.editTextLoginPassword.text.toString()
            val userEmail = binding.editTextLoginEmail.text.toString()
            binding.inputLayoutLoginEmail.error = validator.checkEmail(userEmail)
            binding.inputLayoutLoginPassword.error = validator.checkPassword(userPassword)
            if (binding.inputLayoutLoginEmail.error == null &&
                binding.inputLayoutLoginPassword.error == null
            ) {
                binding.progressBarLogin.visibility = View.VISIBLE
                signIn(userEmail, userPassword)
            }
        }

        binding.textViewLoginSingIn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun signIn(username: String, userpassword: String) {
        ApiService.retrofit.userLogin(
            UserLogin(username, userpassword)
        ).enqueue(object : Callback<Token> {
            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                if (response.isSuccessful) {
                    preferences.token = response.body()?.token.toString()
                    preferences.email = username
                    signIn(username)
                } else
                    when (response.code()) {
                        400 -> toasts(getString(R.string.problem_sign))
                        401 -> toasts(getString(R.string.error_login_or_password))
                    }
                binding.progressBarLogin.visibility = View.GONE
            }

            override fun onFailure(call: Call<Token>, t: Throwable) {
                toasts(t.message.toString())
                binding.progressBarLogin.visibility = View.GONE
            }
        })
    }
    private fun toasts(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
    private fun signIn(username: String) {
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("Name", username)
        startActivity(intent)
    }
}
