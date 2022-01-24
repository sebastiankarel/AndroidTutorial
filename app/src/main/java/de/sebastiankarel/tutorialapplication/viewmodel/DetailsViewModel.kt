package de.sebastiankarel.tutorialapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.sebastiankarel.tutorialapplication.model.Repository
import de.sebastiankarel.tutorialapplication.model.User
import de.sebastiankarel.tutorialapplication.util.Event
import de.sebastiankarel.tutorialapplication.util.getMessageOrDefault
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(private val repository: Repository) : ViewModel() {

    private val _success = MutableLiveData<Event<Boolean>>()
    val success: LiveData<Event<Boolean>> = _success

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = _error

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun fetchUser(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loading.postValue(true)
                val user = repository.getUserById(id)
                _user.postValue(user)
            } catch (e: Exception) {
                _error.postValue(Event(e.getMessageOrDefault()))
            } finally {
                _loading.postValue(false)
            }
        }
    }

    fun deleteUser(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loading.postValue(true)
                repository.deleteUser(id)
                _success.postValue(Event(true))
            } catch (e: Exception) {
                _error.postValue(Event(e.getMessageOrDefault()))
            } finally {
                _loading.postValue(false)
            }
        }
    }
}