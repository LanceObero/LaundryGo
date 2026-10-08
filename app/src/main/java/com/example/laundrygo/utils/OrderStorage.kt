package com.example.laundrygo.utils

import android.content.Context
import com.example.laundrygo.data.model.Order
import org.json.JSONArray
import org.json.JSONObject

object OrderStorage {

    private const val PREF_NAME = "LaundryGoOrders"
    private const val ORDERS_KEY = "orders"

    fun saveOrder(context: Context, order: Order) {

        val preferences = context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )

        val orders = getOrders(context)

        orders.add(0, order)

        val jsonArray = JSONArray()

        for (item in orders) {

            val jsonObject = JSONObject()

            jsonObject.put("orderNumber", item.orderNumber)
            jsonObject.put("serviceName", item.serviceName)
            jsonObject.put("quantity", item.quantity)
            jsonObject.put("pricePerKg", item.pricePerKg)
            jsonObject.put("total", item.total)
            jsonObject.put("deliveryOption", item.deliveryOption)
            jsonObject.put("date", item.date)
            jsonObject.put("notes", item.notes)
            jsonObject.put("status", item.status)

            jsonArray.put(jsonObject)
        }

        preferences
            .edit()
            .putString(ORDERS_KEY, jsonArray.toString())
            .apply()
    }

    fun getOrders(context: Context): ArrayList<Order> {

        val preferences = context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )

        val jsonString = preferences.getString(
            ORDERS_KEY,
            null
        )

        val orders = ArrayList<Order>()

        if (jsonString.isNullOrEmpty()) {
            return orders
        }

        try {

            val jsonArray = JSONArray(jsonString)

            for (i in 0 until jsonArray.length()) {

                val jsonObject = jsonArray.getJSONObject(i)

                orders.add(
                    Order(
                        orderNumber = jsonObject.getString("orderNumber"),
                        serviceName = jsonObject.getString("serviceName"),
                        quantity = jsonObject.getDouble("quantity"),
                        pricePerKg = jsonObject.getInt("pricePerKg"),
                        total = jsonObject.getDouble("total"),
                        deliveryOption = jsonObject.getString("deliveryOption"),
                        date = jsonObject.getString("date"),
                        notes = jsonObject.getString("notes"),
                        status = jsonObject.getString("status")
                    )
                )
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return orders
    }

    fun getLatestOrder(context: Context): Order? {
        return getOrders(context).firstOrNull()
    }
}