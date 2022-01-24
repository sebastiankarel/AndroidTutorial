package de.sebastiankarel.tutorialapplication.util

import android.widget.ImageView
import coil.load
import coil.transform.CircleCropTransformation
import de.sebastiankarel.tutorialapplication.R
import de.sebastiankarel.tutorialapplication.model.persistence.PhotoDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ImageLoaderImpl(private val photoDao: PhotoDao) : ImageLoader {

    private val scope = CoroutineScope(Dispatchers.IO + Job())

    override fun loadImage(imageView: ImageView, photoId: Long) {
        scope.launch {
            try {
                val photo = photoDao.getPhotoById(photoId)
                if (photo.isLocal()) {
                    imageView.load(photo.imageData?.toBitmap()) {
                        crossfade(true)
                        placeholder(R.drawable.baseline_photo_camera_24)
                        transformations(CircleCropTransformation())
                    }
                } else {
                    imageView.load(photo.imageUrl) {
                        crossfade(true)
                        placeholder(R.drawable.baseline_photo_camera_24)
                        transformations(CircleCropTransformation())
                    }
                }
            } catch (e: Exception) {
                imageView.load(R.drawable.ic_broken_image) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
            }
        }
    }
}