package com.example.laundrygo.ui.orders

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.laundrygo.R
import com.example.laundrygo.utils.OrderStorage
import java.util.Locale

class OrderDetailsActivity : AppCompatActivity() {

    private lateinit var btnBack: TextView

    private lateinit var tvOrderNumber: TextView
    private lateinit var tvStatus: TextView
    private lateinit var tvService: TextView
    private lateinit var tvQuantity: TextView
    private lateinit var tvTotal: TextView

    private lateinit var tvDate: TextView
    private lateinit var tvDeliveryOption: TextView
    private lateinit var tvNotes: TextView

    // Progress labels
    private lateinit var tvProgressReceived: TextView
    private lateinit var tvProgressAccepted: TextView
    private lateinit var tvProgressWashing: TextView
    private lateinit var tvProgressDrying: TextView
    private lateinit var tvProgressReady: TextView
    private lateinit var tvProgressCompleted: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        initializeViews()
        setupListeners()
        loadOrder()
    }

    override fun onResume() {
        super.onResume()

        if (::tvOrderNumber.isInitialized) {
            loadOrder()
        }
    }

    private fun initializeViews() {

        btnBack = findViewById(R.id.btnBack)

        tvOrderNumber = findViewById(R.id.tvOrderNumber)
        tvStatus = findViewById(R.id.tvStatus)
        tvService = findViewById(R.id.tvService)
        tvQuantity = findViewById(R.id.tvQuantity)
        tvTotal = findViewById(R.id.tvTotal)

        tvDate = findViewById(R.id.tvDate)
        tvDeliveryOption = findViewById(R.id.tvDeliveryOption)
        tvNotes = findViewById(R.id.tvNotes)

        tvProgressReceived =
            findViewById(R.id.tvProgressReceived)

        tvProgressAccepted =
            findViewById(R.id.tvProgressAccepted)

        tvProgressWashing =
            findViewById(R.id.tvProgressWashing)

        tvProgressDrying =
            findViewById(R.id.tvProgressDrying)

        tvProgressReady =
            findViewById(R.id.tvProgressReady)

        tvProgressCompleted =
            findViewById(R.id.tvProgressCompleted)
    }

    private fun setupListeners() {

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun loadOrder() {

        val order = OrderStorage.getLatestOrder(this)

        if (order == null) {
            return
        }

        tvOrderNumber.text =
            "Order #${order.orderNumber}"

        tvStatus.text =
            order.status

        tvService.text =
            order.serviceName

        tvQuantity.text =
            String.format(
                Locale.US,
                "%.1f kg",
                order.quantity
            )

        tvTotal.text =
            String.format(
                Locale.US,
                "₱%.2f",
                order.total
            )

        tvDate.text =
            order.date

        tvDeliveryOption.text =
            order.deliveryOption

        tvNotes.text =
            if (order.notes.isEmpty()) {
                "No notes"
            } else {
                order.notes
            }

        updateOrderProgress(order.status)
    }

    private fun updateOrderProgress(status: String) {

        val stages = listOf(
            tvProgressReceived,
            tvProgressAccepted,
            tvProgressWashing,
            tvProgressDrying,
            tvProgressReady,
            tvProgressCompleted
        )

        val currentStage = when (status) {

            "Pending" -> 0

            "Accepted" -> 1

            "Washing" -> 2

            "Drying" -> 3

            "Ready for Pickup" -> 4

            "Completed" -> 5

            else -> 0
        }

        for (i in stages.indices) {

            if (i <= currentStage) {

                stages[i].text =
                    "● ${getStageName(i)}"

                stages[i].setTextColor(
                    getColor(R.color.laundry_blue)
                )

            } else {

                stages[i].text =
                    "○ ${getStageName(i)}"

                stages[i].setTextColor(
                    getColor(R.color.gray_button)
                )
            }
        }
    }

    private fun getStageName(index: Int): String {

        return when (index) {

            0 -> "Order Received"

            1 -> "Accepted"

            2 -> "Washing"

            3 -> "Drying"

            4 -> "Ready for Pickup"

            5 -> "Completed"

            else -> ""
        }
    }
}