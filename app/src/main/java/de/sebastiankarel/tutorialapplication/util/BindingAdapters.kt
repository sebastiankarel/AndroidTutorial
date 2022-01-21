package de.sebastiankarel.tutorialapplication.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.textfield.TextInputEditText
import de.sebastiankarel.tutorialapplication.R
import de.sebastiankarel.tutorialapplication.model.User
import de.sebastiankarel.tutorialapplication.view.UserListAdapter

@BindingAdapter("user_adapter")
fun userAdapter(rv: RecyclerView, adapter: ListAdapter<User, UserListAdapter.ListItemViewHolder>) {
    val llm = LinearLayoutManager(rv.context)
    val itemDecor = DividerItemDecoration(rv.context, llm.orientation)
    rv.layoutManager = llm
    rv.addItemDecoration(itemDecor)
    rv.adapter = adapter
}

@BindingAdapter(value = ["user", "preferLargeImage"], requireAll = false)
fun user(imageView: ImageView, user: User?, preferLargeImage: Boolean = false) {
    if (preferLargeImage) {
        user?.imageUrl?.let {
            imageView.load(it) {
                crossfade(true)
                placeholder(R.drawable.baseline_photo_camera_24)
                transformations(CircleCropTransformation())
            }
        } ?: run {
            user?.thumbUrl?.let {
                imageView.load(it) {
                    crossfade(true)
                    placeholder(R.drawable.baseline_photo_camera_24)
                    transformations(CircleCropTransformation())
                }
            }
        }
    } else {
        user?.thumbUrl?.let {
            imageView.load(it) {
                crossfade(true)
                placeholder(R.drawable.baseline_photo_camera_24)
                transformations(CircleCropTransformation())
            }
        } ?: run {
            user?.imageUrl?.let {
                imageView.load(it) {
                    crossfade(true)
                    placeholder(R.drawable.baseline_photo_camera_24)
                    transformations(CircleCropTransformation())
                }
            }
        }
    }
    user?.imageData?.let {
        val options = BitmapFactory.Options().apply { inMutable = true }
        val bmp = BitmapFactory.decodeByteArray(it, 0, it.size, options)
        imageView.load(bmp) {
            crossfade(true)
            placeholder(R.drawable.baseline_photo_camera_24)
            transformations(CircleCropTransformation())
        }
    }
}

@BindingAdapter("bmpImage")
fun bmpImage(imageView: ImageView, bitmap: Bitmap?) {
    if (bitmap == null) return
    imageView.load(bitmap) {
        crossfade(true)
        placeholder(R.drawable.baseline_photo_camera_24)
        transformations(CircleCropTransformation())
    }
}

@BindingAdapter("textChangedListener")
fun textChangedListener(et: TextInputEditText, listener: (newText: String) -> Unit) {
    et.addTextChangedListener { listener(it.toString()) }
}