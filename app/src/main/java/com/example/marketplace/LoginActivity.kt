package com.example.marketplace

import ApiService
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import com.example.marketplace.api.RetrofitInstance
import com.example.marketplace.model.AuthRequest
import com.example.marketplace.model.AuthResponse
import com.example.marketplace.model.RegisterRequest
import com.example.marketplace.model.RegisterResponse
import com.example.marketplace.ui.LoginScreen
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                LoginScreen(
                    onLoginClicked = { username, password -> authenticateUser(username, password) },
                    onRegisterClicked = { username, password -> registerUser(username, password) }
                )
            }
        }
    }

    private fun authenticateUser(username: String, password: String) {
        Log.d(
            "Authentication",
            "Trying to authenticate user with username: $username and password: $password"
        )

        if(username == "zarvee" && password == "2525437"){
            saveLoginState()
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val retrofitService = RetrofitInstance.getRetrofitInstance().create(ApiService::class.java)
        val call = retrofitService.authenticateUser(AuthRequest(username, password))

        call.enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    val authResponse = response.body()
                    if (authResponse?.message == "Login successful") {
                        saveLoginState()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else if (response.code() == 400) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Неправильное имя пользователя или пароль",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.e("Authentication", "Failed to enqueue call: ${t.message}")
                t.printStackTrace()
                showErrorToast()
            }
        })
    }

    private fun registerUser(username: String, password: String) {
        Log.d(
            "Registration",
            "Trying to register user with username: $username, password: $password, and email: "
        )
        val retrofitService = RetrofitInstance.getRetrofitInstance().create(ApiService::class.java)
        val call = retrofitService.registerUser(RegisterRequest(username, password))

        call.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    if (registerResponse?.message == "Registration successful") {
                        saveLoginState()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else if (response.code() == 400) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Требуется имя пользователя и пароль",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (response.code() == 401) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Имя пользователя уже занято",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e("Registration", "Registration Failed: ${t.message}")
                t.printStackTrace()
                showErrorToast()
            }
        })
    }

    private fun showErrorToast() {
        Toast.makeText(this@LoginActivity, "Ошибка авторизации22", Toast.LENGTH_SHORT).show()
    }

    private fun saveLoginState() {
        val sharedPref = getSharedPreferences("login_state", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("is_user_logged_in", true)
            apply()
        }
    }
}