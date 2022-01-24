package de.sebastiankarel.tutorialapplication.util

import android.widget.ImageView

interface ImageLoader {

    fun loadImage(imageView: ImageView, photoId: Long)
}