package de.sebastiankarel.tutorialapplication.model

import com.google.gson.annotations.SerializedName

data class RemoteUserResponse(
    val results: List<RemoteUser>
)

data class RemoteUser(
    val name: RemoteUserName,
    val email: String,
    val picture: RemoteUserPicture
)

data class RemoteUserName(
    val title: String,
    @SerializedName("first") val firstName: String,
    @SerializedName("last") val lastName: String
)

data class RemoteUserPicture(
    val thumbnail: String
)