package de.sebastiankarel.tutorialapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.sebastiankarel.tutorialapplication.model.AuthRepository
import de.sebastiankarel.tutorialapplication.model.Repository
import de.sebastiankarel.tutorialapplication.model.persistence.AppPreferences
import de.sebastiankarel.tutorialapplication.util.Event
import de.sebastiankarel.tutorialapplication.util.getMessageOrDefault
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel(
    private val repository: Repository,
    private val authRepository: AuthRepository,
    private val appPreferences: AppPreferences
) : ViewModel() {

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = _error

    fun users() = repository.users()

    fun fetchUser() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentPageNumber = appPreferences.getPageNumber()
                repository.fetchUsers(currentPageNumber)
                appPreferences.incrementPageNumber()
            } catch (e: Exception) {
                Log.e(ListViewModel::class.java.simpleName, "Error while fetching user", e)
                _error.postValue(Event(e.getMessageOrDefault()))
            }
        }
    }

    fun deleteUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.clearAllUsers()
                appPreferences.resetPageNumber()
            } catch (e: Exception) {
                Log.e(ListViewModel::class.java.simpleName, "Error while deleting user", e)
                _error.postValue(Event(e.getMessageOrDefault()))
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                repository.clearAllUsers()
                appPreferences.resetPageNumber()
            } catch (e: Exception) {
                Log.e(ListViewModel::class.java.simpleName, "Error while deleting user", e)
                _error.postValue(Event(e.getMessageOrDefault()))
            } finally {
                authRepository.logout()
            }
        }
    }
}