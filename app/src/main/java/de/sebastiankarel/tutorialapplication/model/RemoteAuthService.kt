package de.sebastiankarel.tutorialapplication.model

import retrofit2.http.Body
import retrofit2.http.POST

interface RemoteAuthService {

    @POST("api/authaccount/registration")
    suspend fun register(@Body body: RemoteRegisterRequestBody): RemoteAuthResponse

    @POST("api/authaccount/login")
    suspend fun login(@Body body: RemoteLoginRequestBody): RemoteAuthResponse
}