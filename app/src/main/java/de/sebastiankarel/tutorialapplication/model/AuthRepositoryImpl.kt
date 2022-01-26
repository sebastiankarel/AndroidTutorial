package de.sebastiankarel.tutorialapplication.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import de.sebastiankarel.tutorialapplication.model.persistence.AppPreferences

class AuthRepositoryImpl(
    private val remoteAuthService: RemoteAuthService,
    private val appPreferences: AppPreferences
) : AuthRepository {

    override suspend fun register(name: String, email: String, password: Int): Result<Boolean> {
        val response = remoteAuthService.register(RemoteRegisterRequestBody(name, email, password))
        return if (response.data == null) {
            Result.failure(Exception(response.message))
        } else {
            Result.success(true)
        }
    }

    override suspend fun login(email: String, password: Int): Result<Boolean> {
        val response = remoteAuthService.login(RemoteLoginRequestBody(email, password))
        return if (response.data == null) {
            Result.failure(Exception(response.message))
        } else {
            appPreferences.setToken(response.data.token)
            Result.success(true)
        }
    }

    override fun logout() {
        appPreferences.removeToken()
    }

    override fun isLoggedIn(): Boolean = appPreferences.getToken() != null

    override fun getLoginStateLiveData(): LiveData<Boolean> {
        return Transformations.map(appPreferences.getTokenLiveData()) { it != null }
    }
}