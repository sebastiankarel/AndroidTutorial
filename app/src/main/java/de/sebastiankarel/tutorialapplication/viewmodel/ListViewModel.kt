package de.sebastiankarel.tutorialapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.sebastiankarel.tutorialapplication.model.User
import de.sebastiankarel.tutorialapplication.model.Repository
import kotlinx.coroutines.launch

class ListViewModel(private val repository: Repository) : ViewModel() {

    private val _items = MutableLiveData<List<User>>()
    val items: LiveData<List<User>> = _items

    fun updateListItems() {
        viewModelScope.launch {
            try {
                val response = repository.getItems()
                _items.postValue(response)
            } catch (e: Exception) {
                Log.e(ListViewModel::class.java.simpleName, "Error in fetching response", e)
            }
        }
    }
}