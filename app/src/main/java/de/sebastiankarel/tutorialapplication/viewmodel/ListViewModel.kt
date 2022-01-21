package de.sebastiankarel.tutorialapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.sebastiankarel.tutorialapplication.model.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel(private val repository: Repository) : ViewModel() {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun users() = repository.users()

    fun fetchUser() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.fetchUsers(1)
            } catch (e: Exception) {
                Log.e(ListViewModel::class.java.simpleName, "Error while fetching user", e)
                _error.postValue(e.message)
            }
        }
    }

    fun deleteUser() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val ids = repository.getAllIds()
                repository.deleteUser(ids.random())
            } catch (e: Exception) {
                Log.e(ListViewModel::class.java.simpleName, "Error while fetching user", e)
                _error.postValue(e.message)
            }
        }
    }
}