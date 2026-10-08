package com.example.laundrygo.ui.services

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.laundrygo.R
import com.example.laundrygo.ui.orders.CreateOrderActivity
import com.google.android.material.card.MaterialCardView

class ServicesActivity : AppCompatActivity() {

    private lateinit var btnBack: TextView
    private lateinit var cardWashFold: MaterialCardView
    private lateinit var cardDryCleaning: MaterialCardView
    private lateinit var cardIroning: MaterialCardView
    private lateinit var cardWashDry: MaterialCardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services)

        initializeViews()
        setupListeners()
    }

    private fun initializeViews() {
        btnBack = findViewById(R.id.btnBack)
        cardWashFold = findViewById(R.id.cardWashFold)
        cardDryCleaning = findViewById(R.id.cardDryCleaning)
        cardIroning = findViewById(R.id.cardIroning)
        cardWashDry = findViewById(R.id.cardWashDry)
    }

    private fun setupListeners() {

        btnBack.setOnClickListener {
            finish()
        }

        cardWashFold.setOnClickListener {
            openCreateOrder("Wash & Fold", 50)
        }

        cardDryCleaning.setOnClickListener {
            openCreateOrder("Dry Cleaning", 100)
        }

        cardIroning.setOnClickListener {
            openCreateOrder("Ironing", 30)
        }

        cardWashDry.setOnClickListener {
            openCreateOrder("Wash + Dry", 80)
        }
    }

    private fun openCreateOrder(serviceName: String, pricePerKg: Int) {

        val intent = Intent(this, CreateOrderActivity::class.java)

        intent.putExtra("SERVICE_NAME", serviceName)
        intent.putExtra("PRICE_PER_KG", pricePerKg)

        startActivity(intent)
    }
}