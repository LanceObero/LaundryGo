package com.example.laundrygo.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.laundrygo.R
import com.example.laundrygo.ui.home.HomeActivity
import com.example.laundrygo.utils.UserStorage
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: MaterialButton
    private lateinit var tvCreateAccount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Automatically open Home if the user is already logged in
        if (UserStorage.isLoggedIn(this)) {
            startActivity(
                Intent(this, HomeActivity::class.java)
            )
            finish()
            return
        }

        setContentView(R.layout.activity_login)

        initializeViews()
        setupListeners()
    }

    private fun initializeViews() {
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvCreateAccount = findViewById(R.id.tvCreateAccount)
    }

    private fun setupListeners() {

        btnLogin.setOnClickListener {

            val email =
                etEmail.text?.toString()?.trim() ?: ""

            val password =
                etPassword.text?.toString()?.trim() ?: ""

            if (email.isEmpty()) {
                etEmail.error = "Email is required"
                etEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                etPassword.error = "Password is required"
                etPassword.requestFocus()
                return@setOnClickListener
            }

            val success = UserStorage.login(
                context = this,
                email = email,
                password = password
            )

            if (success) {

                Toast.makeText(
                    this,
                    "Welcome back!",
                    Toast.LENGTH_SHORT
                ).show()

                val intent =
                    Intent(this, HomeActivity::class.java)

                startActivity(intent)
                finish()

            } else {

                Toast.makeText(
                    this,
                    "Invalid email or password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        tvCreateAccount.setOnClickListener {

            val intent =
                Intent(this, RegisterActivity::class.java)

            startActivity(intent)
        }
    }
}