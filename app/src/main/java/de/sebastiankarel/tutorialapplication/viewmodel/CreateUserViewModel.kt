package de.sebastiankarel.tutorialapplication.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.sebastiankarel.tutorialapplication.model.Repository
import de.sebastiankarel.tutorialapplication.util.Event
import de.sebastiankarel.tutorialapplication.util.getMessageOrDefault
import de.sebastiankarel.tutorialapplication.util.toBitmap
import de.sebastiankarel.tutorialapplication.util.toByteArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateUserViewModel(private val repository: Repository) : ViewModel() {

    private val _success = MutableLiveData<Event<Boolean>>()
    val success: LiveData<Event<Boolean>> = _success

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = _error

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email
    private val _image = MutableLiveData<Bitmap>()
    val image: LiveData<Bitmap> = _image

    val nameChangedListener: (name: String) -> Unit = {
        _name.postValue(it)
    }

    val emailChangedListener: (name: String) -> Unit = {
        _email.postValue(it)
    }

    fun setPhoto(photo: Bitmap) = _image.postValue(photo)

    fun submitUser() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loading.postValue(true)
                val imageData = _image.value?.toByteArray()
                val photoId = if (imageData != null) repository.addPhoto(imageData) else -1
                repository.addUser(_name.value ?: "", _email.value ?: "", photoId)
                clearData()
                _success.postValue(Event(true))
            } catch (e: Exception) {
                _error.postValue(Event(e.getMessageOrDefault()))
            } finally {
                _loading.postValue(false)
            }
        }
    }

    private fun clearData() {
        _name.postValue("")
        _email.postValue("")
        _image.postValue(null)
    }
}