package de.sebastiankarel.tutorialapplication.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.sebastiankarel.tutorialapplication.model.Repository
import de.sebastiankarel.tutorialapplication.util.Event
import de.sebastiankarel.tutorialapplication.util.getMessageOrDefault
import de.sebastiankarel.tutorialapplication.util.toByteArray
import kotlinx.coroutines.launch

class CameraViewModel(private val repository: Repository) : ViewModel() {

    private val _success = MutableLiveData<Event<Long>>()
    val success: LiveData<Event<Long>> = _success

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = _error

    fun storePhoto(photo: Bitmap) {
        viewModelScope.launch {
            try {
                _loading.postValue(true)
                val photoId = repository.addPhoto(photo.toByteArray())
                _success.postValue(Event(photoId))
            } catch (e: Exception) {
                _error.postValue(Event(e.getMessageOrDefault()))
            } finally {
                _loading.postValue(false)
            }
        }
    }
}