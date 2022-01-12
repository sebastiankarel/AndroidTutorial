package de.sebastiankarel.tutorialapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.sebastiankarel.tutorialapplication.model.ListItem
import de.sebastiankarel.tutorialapplication.model.Repository

class ListViewModel(private val repository: Repository) : ViewModel() {

    private val _items = MutableLiveData<List<ListItem>>()
    val items: LiveData<List<ListItem>> = _items

    fun updateListItems() {
        val response = repository.getItems()
        _items.postValue(response)
    }
}