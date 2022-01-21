package de.sebastiankarel.tutorialapplication.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.sebastiankarel.tutorialapplication.model.Repository
import de.sebastiankarel.tutorialapplication.model.User
import de.sebastiankarel.tutorialapplication.util.toByteArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateUserViewModel(private val repository: Repository) : ViewModel() {

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _image = MutableLiveData<Bitmap>()
    val image: LiveData<Bitmap> = _image

    private var name: String = ""
    val nameChangedListener: (name: String) -> Unit = {
        name = it
    }

    private var email: String = ""
    val emailChangedListener: (name: String) -> Unit = {
        email = it
    }

    fun setImage(image: Bitmap?) = _image.postValue(image)

    fun submitUser() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loading.postValue(false)
                val user = User(
                    name = name,
                    email = email,
                    imageData = _image.value?.toByteArray(),
                    timeStamp = System.currentTimeMillis()
                )
                repository.addUser(user)
                _success.postValue(true)
            } catch (e: Exception) {
                _error.postValue(e.message)
            } finally {
                _loading.postValue(false)
            }
        }
    }
}