package com.example.mealmate.utils

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import androidx.annotation.DrawableRes
import java.io.ByteArrayOutputStream

object ImageUtils {

    fun drawableToByteArray(@DrawableRes drawableId: Int, context: Context): ByteArray {
        val drawable = context.getDrawable(drawableId) as BitmapDrawable
        val bitmap = drawable.bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }
}