package com.example.laundrygo.ui.orders

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.laundrygo.R
import com.example.laundrygo.utils.OrderStorage
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.util.Locale

class OrderHistoryActivity : AppCompatActivity() {

    private lateinit var btnBack: TextView
    private lateinit var cardOrder: MaterialCardView
    private lateinit var btnViewOrder: MaterialButton

    private lateinit var tvOrderNumber: TextView
    private lateinit var tvOrderService: TextView
    private lateinit var tvOrderStatus: TextView
    private lateinit var tvOrderQuantity: TextView
    private lateinit var tvOrderTotal: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)

        initializeViews()
        setupListeners()
        loadOrders()
    }

    private fun initializeViews() {

        btnBack = findViewById(R.id.btnBack)

        cardOrder = findViewById(R.id.cardOrder)

        btnViewOrder =
            findViewById(R.id.btnViewOrder)

        tvOrderNumber =
            findViewById(R.id.tvOrderNumber)

        tvOrderService =
            findViewById(R.id.tvOrderService)

        tvOrderStatus =
            findViewById(R.id.tvOrderStatus)

        tvOrderQuantity =
            findViewById(R.id.tvOrderQuantity)

        tvOrderTotal =
            findViewById(R.id.tvOrderTotal)
    }

    private fun setupListeners() {

        btnBack.setOnClickListener {
            finish()
        }

        cardOrder.setOnClickListener {
            openOrderDetails()
        }

        btnViewOrder.setOnClickListener {
            openOrderDetails()
        }
    }

    private fun loadOrders() {

        val latestOrder =
            OrderStorage.getLatestOrder(this)

        if (latestOrder == null) {

            cardOrder.visibility =
                View.GONE

            return
        }

        cardOrder.visibility =
            View.VISIBLE

        tvOrderNumber.text =
            "Order #${latestOrder.orderNumber}"

        tvOrderService.text =
            latestOrder.serviceName

        tvOrderStatus.text =
            latestOrder.status

        tvOrderQuantity.text =
            String.format(
                Locale.US,
                "%.1f kg",
                latestOrder.quantity
            )

        tvOrderTotal.text =
            String.format(
                Locale.US,
                "₱%.2f",
                latestOrder.total
            )
    }

    private fun openOrderDetails() {

        val intent = Intent(
            this,
            OrderDetailsActivity::class.java
        )

        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()

        if (::cardOrder.isInitialized) {
            loadOrders()
        }
    }
}