package de.sebastiankarel.tutorialapplication.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

fun Bitmap.toByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}

fun ByteArray.toBitmap(): Bitmap {
    val options = BitmapFactory.Options().apply { inMutable = true }
    return BitmapFactory.decodeByteArray(this, 0, size, options)
}

fun Exception.getMessageOrDefault(): String {
    return "${message ?: "An unknown error occurred."} (cause: ${cause?.message ?: "unknown"})"
}