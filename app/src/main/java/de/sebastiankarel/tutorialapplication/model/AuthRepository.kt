package de.sebastiankarel.tutorialapplication.model

import androidx.lifecycle.LiveData

interface AuthRepository {

    suspend fun register(name: String, email: String, password: Int): Result<Boolean>

    suspend fun login(email: String, password: Int): Result<Boolean>

    fun logout()

    fun isLoggedIn(): Boolean

    fun getLoginStateLiveData(): LiveData<Boolean>
}