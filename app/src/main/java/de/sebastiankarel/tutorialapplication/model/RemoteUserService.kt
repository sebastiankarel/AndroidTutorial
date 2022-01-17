package de.sebastiankarel.tutorialapplication.model

import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteUserService {

    @GET("api/")
    suspend fun getUsers(@Query("results") numResults: Int): RemoteUserResponse
}