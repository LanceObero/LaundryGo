package com.example.laundrygo.data.model

data class Order(
    val orderNumber: String,
    val serviceName: String,
    val quantity: Double,
    val pricePerKg: Int,
    val total: Double,
    val deliveryOption: String,
    val date: String,
    val notes: String,
    val status: String
)