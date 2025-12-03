package com.example.mealmate.data.local.AddRecipe


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.mealmate.data.Mapper.Converters
import com.example.mealmate.data.local.Auth.UserEntity

@Entity(
    tableName = "recipes",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
@TypeConverters(Converters::class)
data class RecipeTableEntity(
    @PrimaryKey(autoGenerate = true)
    val recipeId: Long = 0L,
    val userId:Long,
    val title: String,
    val description: String,
    val recipeImage: ByteArray?,
    val category: String,
    val style: String,
    val mealType: List<String>,
    val serving: Int,
    val preprationTime: String,
    val cookingTime: String
)
