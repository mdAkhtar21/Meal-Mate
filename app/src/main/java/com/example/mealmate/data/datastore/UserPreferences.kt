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
        val USER_ID_KEY= stringPreferencesKey("user_id")
    }

    suspend fun saveUser(userId:Long,email: String, username: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID_KEY]=userId.toString()
            prefs[EMAIL_KEY] = email
            prefs[USERNAME_KEY] = username

        }
    }

    fun getUser(): Flow<Triple<Long,String?, String?>> {
        return context.dataStore.data.map { prefs ->
            val id=prefs[USER_ID_KEY]?.toLongOrNull()?:0L
            val email = prefs[EMAIL_KEY]
            val username = prefs[USERNAME_KEY]
            Triple(id,email, username)
        }
    }
    suspend fun clearUser() {
        context.dataStore.edit { it.clear() }
    }

    fun isUserLoggedIn(): Flow<Boolean> {
        return context.dataStore.data.map { prefs ->
            prefs[EMAIL_KEY] != null && prefs[USERNAME_KEY] != null && prefs[USER_ID_KEY]!=null
        }
    }
}