package com.goodapps.threescreens.domain

import android.content.Context

private const val STORAGE_NAME = "three_screens_prefs"
private const val USERNAME = "username"

object UserRepository {

    fun saveUsername(context: Context, username: String) {
        context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit()
            .putString(USERNAME, username).apply()
    }

    fun clearUsername(context: Context) {
        context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit()
            .remove(USERNAME).apply()
    }

    fun getUsername(context: Context): String {
        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
            .getString(USERNAME, "") ?: ""
    }

}