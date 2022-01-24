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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateUserViewModel(private val repository: Repository) : ViewModel() {

    private val _success = MutableLiveData<Event<Boolean>>()
    val success: LiveData<Event<Boolean>> = _success

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = _error

    private var photoId: Long = -1
    private val _image = MutableLiveData<Bitmap>()
    val image: LiveData<Bitmap> = _image

    private var _name: String = ""
    val nameChangedListener: (name: String) -> Unit = {
        _name = it
    }

    private var _email: String = ""
    val emailChangedListener: (name: String) -> Unit = {
        _email = it
    }

    fun setPhoto(photoId: Long) {
        if (photoId < 0) return
        viewModelScope.launch {
            try {
                _loading.postValue(true)
                if (this@CreateUserViewModel.photoId >= 0) {
                    repository.deletePhotoById(this@CreateUserViewModel.photoId)
                }
                this@CreateUserViewModel.photoId = photoId
                val photo = repository.getPhotoById(photoId)
                _image.postValue(photo?.imageData?.toBitmap())
            } catch (e: Exception) {
                _error.postValue(Event(e.getMessageOrDefault()))
            } finally {
                _loading.postValue(false)
            }
        }
    }

    fun submitUser() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loading.postValue(false)
                repository.addUser(_name, _email, photoId)
                _success.postValue(Event(true))
            } catch (e: Exception) {
                _error.postValue(Event(e.getMessageOrDefault()))
            } finally {
                _loading.postValue(false)
            }
        }
    }

    fun getEmail(): String = _email

    fun getName(): String = _name
}