package com.example.laundrygo.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.laundrygo.R
import com.example.laundrygo.ui.orders.CreateOrderActivity
import com.example.laundrygo.ui.orders.OrderDetailsActivity
import com.example.laundrygo.ui.orders.OrderHistoryActivity
import com.example.laundrygo.ui.profile.ProfileActivity
import com.example.laundrygo.ui.services.ServicesActivity
import com.example.laundrygo.utils.OrderStorage
import com.example.laundrygo.utils.UserStorage
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class HomeActivity : AppCompatActivity() {

    private lateinit var tvUserName: TextView
    private lateinit var btnNewOrder: MaterialButton
    private lateinit var bottomNavigation: BottomNavigationView

    private lateinit var cardWash: MaterialCardView
    private lateinit var cardDry: MaterialCardView

    private lateinit var cardCurrentOrder: MaterialCardView
    private lateinit var tvOrderNumber: TextView
    private lateinit var tvOrderStatus: TextView
    private lateinit var tvViewOrder: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initializeViews()
        setupListeners()
        loadUserProfile()
    }

    override fun onResume() {
        super.onResume()

        if (::cardCurrentOrder.isInitialized) {
            loadUserProfile()
            loadLatestOrder()
        }
    }

    private fun initializeViews() {

        tvUserName = findViewById(R.id.tvUserName)
        btnNewOrder = findViewById(R.id.btnNewOrder)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        cardWash = findViewById(R.id.cardWash)
        cardDry = findViewById(R.id.cardDry)

        cardCurrentOrder =
            findViewById(R.id.cardCurrentOrder)

        tvOrderNumber =
            findViewById(R.id.tvOrderNumber)

        tvOrderStatus =
            findViewById(R.id.tvOrderStatus)

        tvViewOrder =
            findViewById(R.id.tvViewOrder)
    }

    private fun setupListeners() {

        // New Order
        btnNewOrder.setOnClickListener {
            openServices()
        }

        // Wash & Fold
        cardWash.setOnClickListener {
            openCreateOrder(
                serviceName = "Wash & Fold",
                pricePerKg = 50
            )
        }

        // Dry Cleaning
        cardDry.setOnClickListener {
            openCreateOrder(
                serviceName = "Dry Cleaning",
                pricePerKg = 100
            )
        }

        // Current Order
        cardCurrentOrder.setOnClickListener {
            openOrderDetails()
        }

        tvViewOrder.setOnClickListener {
            openOrderDetails()
        }

        // Bottom Navigation
        bottomNavigation.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.nav_home -> {
                    true
                }

                R.id.nav_orders -> {
                    openOrderHistory()
                    true
                }

                R.id.nav_services -> {
                    openServices()
                    true
                }

                R.id.nav_profile -> {
                    openProfile()
                    true
                }

                else -> false
            }
        }
    }

    private fun loadUserProfile() {

        val name = UserStorage.getName(this)

        tvUserName.text = name
    }

    private fun loadLatestOrder() {

        val latestOrder =
            OrderStorage.getLatestOrder(this)

        if (latestOrder == null) {

            cardCurrentOrder.visibility =
                View.GONE

            return
        }

        cardCurrentOrder.visibility =
            View.VISIBLE

        tvOrderNumber.text =
            "Order #${latestOrder.orderNumber}"

        tvOrderStatus.text =
            latestOrder.status
    }

    private fun openServices() {

        val intent = Intent(
            this,
            ServicesActivity::class.java
        )

        startActivity(intent)
    }

    private fun openOrderHistory() {

        val intent = Intent(
            this,
            OrderHistoryActivity::class.java
        )

        startActivity(intent)
    }

    private fun openOrderDetails() {

        val intent = Intent(
            this,
            OrderDetailsActivity::class.java
        )

        startActivity(intent)
    }

    private fun openProfile() {

        val intent = Intent(
            this,
            ProfileActivity::class.java
        )

        startActivity(intent)
    }

    private fun openCreateOrder(
        serviceName: String,
        pricePerKg: Int
    ) {

        val intent = Intent(
            this,
            CreateOrderActivity::class.java
        )

        intent.putExtra(
            "SERVICE_NAME",
            serviceName
        )

        intent.putExtra(
            "PRICE_PER_KG",
            pricePerKg
        )

        startActivity(intent)
    }
}