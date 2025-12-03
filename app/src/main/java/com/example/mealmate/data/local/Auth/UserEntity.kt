package com.example.mealmate.data.local.Auth

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.mealmate.domain.model.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val email: String,
    val username: String,
    val password: String
)