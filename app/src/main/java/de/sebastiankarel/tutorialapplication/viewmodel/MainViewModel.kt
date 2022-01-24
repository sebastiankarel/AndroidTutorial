package de.sebastiankarel.tutorialapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.sebastiankarel.tutorialapplication.model.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    fun deleteSpuriousPhotos() {
        viewModelScope.launch {
            try {
                repository.clearSpuriousImages()
            } catch (e: Exception) {
                Log.e(MainViewModel::class.java.simpleName, "Error while deleting spurious photos.", e)
            }
        }
    }
}