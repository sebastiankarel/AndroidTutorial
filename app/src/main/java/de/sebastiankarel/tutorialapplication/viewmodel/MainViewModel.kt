package de.sebastiankarel.tutorialapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import de.sebastiankarel.tutorialapplication.model.AuthRepository

class MainViewModel(private val authRepository: AuthRepository) : ViewModel() {

    val loginStateLiveData: LiveData<Boolean> = authRepository.getLoginStateLiveData()

}