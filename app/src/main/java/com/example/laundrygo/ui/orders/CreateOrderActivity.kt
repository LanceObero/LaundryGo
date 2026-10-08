package com.example.laundrygo.ui.orders

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.laundrygo.R
import com.example.laundrygo.data.model.Order
import com.example.laundrygo.utils.OrderStorage
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar
import java.util.Locale

class CreateOrderActivity : AppCompatActivity() {

    private lateinit var tvSelectedService: TextView
    private lateinit var tvPricePerKg: TextView
    private lateinit var etQuantity: TextInputEditText
    private lateinit var tvTotalPrice: TextView
    private lateinit var tvSelectedDate: TextView
    private lateinit var etNotes: TextInputEditText

    private lateinit var btnPickup: MaterialButton
    private lateinit var btnDropOff: MaterialButton
    private lateinit var btnSelectDate: MaterialButton
    private lateinit var btnPlaceOrder: MaterialButton
    private lateinit var btnBack: TextView

    private var serviceName = ""
    private var pricePerKg = 0
    private var selectedDate = ""
    private var deliveryOption = "Pickup"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_order)

        getServiceDetails()
        initializeViews()
        setupListeners()

        updateServiceInfo()
        updateDeliveryButtons()
        calculateTotal()
    }

    private fun getServiceDetails() {

        serviceName = intent.getStringExtra(
            "SERVICE_NAME"
        ) ?: "Wash & Fold"

        pricePerKg = intent.getIntExtra(
            "PRICE_PER_KG",
            50
        )
    }

    private fun initializeViews() {

        btnBack = findViewById(R.id.btnBack)

        tvSelectedService = findViewById(R.id.tvSelectedService)
        tvPricePerKg = findViewById(R.id.tvPricePerKg)

        etQuantity = findViewById(R.id.etQuantity)

        tvTotalPrice = findViewById(R.id.tvTotalPrice)

        btnPickup = findViewById(R.id.btnPickup)
        btnDropOff = findViewById(R.id.btnDropOff)

        btnSelectDate = findViewById(R.id.btnSelectDate)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)

        etNotes = findViewById(R.id.etNotes)

        btnPlaceOrder = findViewById(R.id.btnPlaceOrder)
    }

    private fun setupListeners() {

        btnBack.setOnClickListener {
            finish()
        }

        etQuantity.setOnKeyListener { _, _, _ ->
            calculateTotal()
            false
        }

        btnPickup.setOnClickListener {

            deliveryOption = "Pickup"

            updateDeliveryButtons()
        }

        btnDropOff.setOnClickListener {

            deliveryOption = "Drop-off"

            updateDeliveryButtons()
        }

        btnSelectDate.setOnClickListener {
            showDatePicker()
        }

        btnPlaceOrder.setOnClickListener {
            placeOrder()
        }
    }

    private fun updateServiceInfo() {

        tvSelectedService.text = serviceName

        tvPricePerKg.text =
            "₱$pricePerKg per kg"
    }

    private fun calculateTotal() {

        val quantity =
            etQuantity.text?.toString()?.toDoubleOrNull()
                ?: 0.0

        val total = quantity * pricePerKg

        tvTotalPrice.text =
            String.format(
                Locale.US,
                "₱%.2f",
                total
            )
    }

    private fun updateDeliveryButtons() {

        if (deliveryOption == "Pickup") {

            btnPickup.setBackgroundColor(
                getColor(R.color.laundry_blue)
            )

            btnDropOff.setBackgroundColor(
                getColor(R.color.gray_button)
            )

        } else {

            btnPickup.setBackgroundColor(
                getColor(R.color.gray_button)
            )

            btnDropOff.setBackgroundColor(
                getColor(R.color.laundry_blue)
            )
        }
    }

    private fun showDatePicker() {

        val calendar = Calendar.getInstance()

        val datePicker = DatePickerDialog(
            this,
            { _, year, month, day ->

                selectedDate = String.format(
                    Locale.US,
                    "%02d/%02d/%04d",
                    month + 1,
                    day,
                    year
                )

                tvSelectedDate.text =
                    selectedDate

            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePicker.datePicker.minDate =
            calendar.timeInMillis

        datePicker.show()
    }

    private fun placeOrder() {

        val quantityText =
            etQuantity.text?.toString()?.trim()

        if (quantityText.isNullOrEmpty()) {

            etQuantity.error =
                "Enter laundry quantity"

            etQuantity.requestFocus()

            return
        }

        val quantity =
            quantityText.toDoubleOrNull()

        if (quantity == null || quantity <= 0) {

            etQuantity.error =
                "Enter a valid quantity"

            etQuantity.requestFocus()

            return
        }

        if (selectedDate.isEmpty()) {

            Toast.makeText(
                this,
                "Please select a preferred date",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val total =
            quantity * pricePerKg

        val orderNumber =
            "LG-" + System.currentTimeMillis()
                .toString()
                .takeLast(6)

        val notes =
            etNotes.text?.toString()?.trim() ?: ""

        val order = Order(

            orderNumber = orderNumber,

            serviceName = serviceName,

            quantity = quantity,

            pricePerKg = pricePerKg,

            total = total,

            deliveryOption = deliveryOption,

            date = selectedDate,

            notes = notes,

            status = "Pending"
        )

        OrderStorage.saveOrder(
            this,
            order
        )

        Toast.makeText(
            this,
            "Order $orderNumber placed successfully!",
            Toast.LENGTH_LONG
        ).show()

        val intent = Intent(
            this,
            OrderDetailsActivity::class.java
        )

        startActivity(intent)

        finish()
    }
}