package de.sebastiankarel.tutorialapplication.viewmodel

import androidx.lifecycle.*
import de.sebastiankarel.tutorialapplication.model.AuthRepository
import de.sebastiankarel.tutorialapplication.model.Repository
import de.sebastiankarel.tutorialapplication.util.Event
import de.sebastiankarel.tutorialapplication.util.getMessageOrDefault
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _success = MutableLiveData<Event<Boolean>>()
    val success: LiveData<Event<Boolean>> = _success

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email
    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

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
            this.value = !_email.value.isNullOrEmpty() && !_password.value.isNullOrEmpty()
        }
        addSource(_email) { update() }
        addSource(_password) { update() }
    }
    val buttonEnabled: LiveData<Boolean> = _buttonEnabled

    fun isLoggedIn(): Boolean = authRepository.isLoggedIn()

    fun login() {
        viewModelScope.launch {
            try {
                _loading.postValue(true)
                val result = authRepository.login(_email.value ?: "", _password.value?.toInt() ?: 0)
                if (result.isSuccess) {
                    _error.postValue(null)
                    _success.postValue(Event(true))
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