package de.sebastiankarel.tutorialapplication.viewmodel

import androidx.lifecycle.*
import de.sebastiankarel.tutorialapplication.model.AuthRepository
import de.sebastiankarel.tutorialapplication.model.Repository
import de.sebastiankarel.tutorialapplication.util.Event
import de.sebastiankarel.tutorialapplication.util.getMessageOrDefault
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _success = MutableLiveData<Event<Pair<String, String>>>()
    val success: LiveData<Event<Pair<String, String>>> = _success

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email
    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    val nameChangedListener: (name: String) -> Unit = {
        _name.postValue(it)
        _error.postValue(null)
    }

    val emailChangedListener: (email: String) -> Unit = {
        _email.postValue(it)
        _error.postValue(null)
    }

    val passwordChangedListener: (password: String) -> Unit = {
        _password.postValue(it)
        _error.postValue(null)
    }

    private val _buttonEnabled = MediatorLiveData<Boolean>().apply {
        fun update() {
            this.value = !_name.value.isNullOrEmpty() && !_email.value.isNullOrEmpty() && !_password.value.isNullOrEmpty()
        }
        addSource(_name) { update() }
        addSource(_email) { update() }
        addSource(_password) { update() }
    }
    val buttonEnabled: LiveData<Boolean> = _buttonEnabled

    fun register() {
        viewModelScope.launch {
            try {
                _loading.postValue(true)
                val result = authRepository.register(_name.value ?: "", _email.value ?: "", _password.value?.toInt() ?: 0)
                if (result.isSuccess) {
                    _error.postValue(null)
                    _success.postValue(Event(Pair(_email.value ?: "", _password.value ?: "")))
                } else {
                    _error.postValue(result.exceptionOrNull()?.message ?: "Unknown error")
                }
            } catch (e: Exception) {
                _error.postValue(e.getMessageOrDefault())
            } finally {
                _loading.postValue(false)
            }
        }
    }
}