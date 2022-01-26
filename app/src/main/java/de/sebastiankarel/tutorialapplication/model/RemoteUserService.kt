package de.sebastiankarel.tutorialapplication.model

import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteUserService {

    @GET("api/users")
    suspend fun getUsers(@Query("page") page: Int): RemoteUserResponse
}