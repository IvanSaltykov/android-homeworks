package com.example.todoapp.user_interface.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.R
import com.example.todoapp.databinding.ActivityRegisterBinding
import com.example.todoapp.models.Token
import com.example.todoapp.models.UserCreate
import com.example.todoapp.preferences.PreferencesManager
import com.example.todoapp.requests_server.ApiService
import com.example.todoapp.user_interface.validation.Validator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val preferences = PreferencesManager(this)
        binding.buttonRegister.setOnClickListener {
            val validator = Validator(this)
            val userPassword = binding.editTextRegisterPassword.text.toString()
            val userConfirmPassword = binding.editTextRegisterConfirmPassword.text.toString()
            val userEmail = binding.editTextRegisterEmail.text.toString()
            val userName = binding.editTextRegisterName.text.toString()
            binding.inputLayoutRegisterEmail.error = validator.checkEmail(userEmail)
            binding.inputLayoutRegisterPassword.error = validator.checkPassword(userPassword)
            binding.inputLayoutRegisterConfirmPassword.error =
                validator.checkConfirmPassword(userPassword, userConfirmPassword)
            binding.inputLayoutRegisterName.error = validator.checkName(userName)
            if (binding.inputLayoutRegisterPassword.error == null &&
                binding.inputLayoutRegisterEmail.error == null &&
                binding.inputLayoutRegisterConfirmPassword.error == null &&
                binding.inputLayoutRegisterName.error == null
            ) {
                binding.progressBarRegister.visibility = View.VISIBLE
                ApiService.retrofit.userCreate(
                    UserCreate(userName, userEmail, userPassword)
                ).enqueue(object : Callback<Token> {
                    override fun onResponse(call: Call<Token>, response: Response<Token>) {
                        if (response.isSuccessful) {
                            preferences.token = response.body()?.token.toString()
                            val intent = Intent(
                                this@RegisterActivity,
                                ProfileActivity::class.java
                            )
                            intent.putExtra("Name", userEmail)
                            startActivity(intent)
                            finish()
                        } else
                            Toast.makeText(
                                this@RegisterActivity,
                                getString(R.string.incorrect_input),
                                Toast.LENGTH_LONG
                            ).show()
                        binding.progressBarRegister.visibility = View.GONE
                    }
                    override fun onFailure(call: Call<Token>, t: Throwable) {
                        Toast.makeText(this@RegisterActivity, t.message, Toast.LENGTH_LONG).show()
                        binding.progressBarRegister.visibility = View.GONE
                    }
                })
            }
        }
        binding.textViewRegisterSingIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
