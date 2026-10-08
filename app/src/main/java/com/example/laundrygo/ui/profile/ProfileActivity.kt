package com.example.laundrygo.ui.profile

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.laundrygo.R
import com.example.laundrygo.ui.auth.LoginActivity
import com.example.laundrygo.ui.orders.OrderHistoryActivity
import com.example.laundrygo.utils.UserStorage
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class ProfileActivity : AppCompatActivity() {

    private lateinit var btnBack: TextView

    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvPhone: TextView
    private lateinit var tvProfileName: TextView

    private lateinit var cardPersonalInfo: MaterialCardView
    private lateinit var cardOrderHistory: MaterialCardView

    private lateinit var btnLogout: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        initializeViews()
        setupListeners()
        loadProfile()
    }

    override fun onResume() {
        super.onResume()

        if (::tvName.isInitialized) {
            loadProfile()
        }
    }

    private fun initializeViews() {

        btnBack = findViewById(R.id.btnBack)

        tvName = findViewById(R.id.tvName)
        tvEmail = findViewById(R.id.tvEmail)
        tvPhone = findViewById(R.id.tvPhone)
        tvProfileName = findViewById(R.id.tvProfileName)

        cardPersonalInfo =
            findViewById(R.id.cardPersonalInfo)

        cardOrderHistory =
            findViewById(R.id.cardOrderHistory)

        btnLogout =
            findViewById(R.id.btnLogout)
    }

    private fun setupListeners() {

        btnBack.setOnClickListener {
            finish()
        }

        cardPersonalInfo.setOnClickListener {
            Toast.makeText(
                this,
                "Edit profile will be available soon.",
                Toast.LENGTH_SHORT
            ).show()
        }

        cardOrderHistory.setOnClickListener {

            val intent = Intent(
                this,
                OrderHistoryActivity::class.java
            )

            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun loadProfile() {

        val name = UserStorage.getName(this)
        val email = UserStorage.getEmail(this)
        val phone = UserStorage.getPhone(this)

        // Profile header
        tvName.text = name
        tvEmail.text = email

        // Personal information
        tvProfileName.text = name
        tvPhone.text = phone
    }

    private fun logout() {

        UserStorage.logout(this)

        Toast.makeText(
            this,
            "Logged out successfully",
            Toast.LENGTH_SHORT
        ).show()

        val intent = Intent(
            this,
            LoginActivity::class.java
        )

        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)
        finish()
    }
}