package com.example.laundrygo.utils

import android.content.Context

object UserStorage {

    private const val PREF_NAME = "LaundryGoUser"

    private const val KEY_NAME = "name"
    private const val KEY_EMAIL = "email"
    private const val KEY_PHONE = "phone"
    private const val KEY_PASSWORD = "password"
    private const val KEY_LOGGED_IN = "logged_in"

    fun saveUser(
        context: Context,
        name: String,
        email: String,
        phone: String,
        password: String
    ) {

        val preferences = context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )

        preferences.edit()
            .putString(KEY_NAME, name)
            .putString(KEY_EMAIL, email)
            .putString(KEY_PHONE, phone)
            .putString(KEY_PASSWORD, password)
            .putBoolean(KEY_LOGGED_IN, false)
            .apply()
    }

    fun login(
        context: Context,
        email: String,
        password: String
    ): Boolean {

        val preferences = context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )

        val savedEmail =
            preferences.getString(KEY_EMAIL, "")

        val savedPassword =
            preferences.getString(KEY_PASSWORD, "")

        if (
            email == savedEmail &&
            password == savedPassword
        ) {

            preferences.edit()
                .putBoolean(KEY_LOGGED_IN, true)
                .apply()

            return true
        }

        return false
    }

    fun isLoggedIn(context: Context): Boolean {

        val preferences = context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )

        return preferences.getBoolean(
            KEY_LOGGED_IN,
            false
        )
    }

    fun logout(context: Context) {

        val preferences = context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )

        preferences.edit()
            .putBoolean(KEY_LOGGED_IN, false)
            .apply()
    }

    fun getName(context: Context): String {

        val preferences = context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )

        return preferences.getString(
            KEY_NAME,
            "LaundryGo User"
        ) ?: "LaundryGo User"
    }

    fun getEmail(context: Context): String {

        val preferences = context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )

        return preferences.getString(
            KEY_EMAIL,
            "user@example.com"
        ) ?: "user@example.com"
    }

    fun getPhone(context: Context): String {

        val preferences = context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )

        return preferences.getString(
            KEY_PHONE,
            "0912 345 6789"
        ) ?: "0912 345 6789"
    }
}