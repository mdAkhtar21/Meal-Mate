package com.example.mealmate.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_prefs")

class UserPreferences(private val context: Context) {
    companion object {
        val EMAIL_KEY = stringPreferencesKey("email")
        val USERNAME_KEY = stringPreferencesKey("username")
    }

    suspend fun saveUser(email: String, username: String) {
        context.dataStore.edit { prefs ->
            prefs[EMAIL_KEY] = email
            prefs[USERNAME_KEY] = username
        }
    }

    fun getUser(): Flow<Pair<String?, String?>> {
        return context.dataStore.data.map { prefs ->
            val email = prefs[EMAIL_KEY]
            val username = prefs[USERNAME_KEY]
            Pair(email, username)
        }
    }
}