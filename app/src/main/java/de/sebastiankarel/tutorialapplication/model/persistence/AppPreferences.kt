package de.sebastiankarel.tutorialapplication.model.persistence

import androidx.lifecycle.LiveData

interface AppPreferences {

    fun getToken(): String?

    fun setToken(token: String)

    fun removeToken()

    fun getTokenLiveData(): LiveData<String?>

    fun getPageNumber(): Int

    fun incrementPageNumber()

    fun resetPageNumber()
}