package de.sebastiankarel.tutorialapplication.model

import com.google.gson.annotations.SerializedName

data class RemoteUserResponse(
    @SerializedName("\$id") val _id: String,
    @SerializedName("page") val page: Int,
    @SerializedName("per_page") val perPage: Int,
    @SerializedName("totalrecord") val totalRecord: Int,
    @SerializedName("total_pages") val totalPages: Int,
    val data: List<RemoteUser>
)

data class RemoteUser(
    @SerializedName("\$id") val _id: String,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("profilepicture") val profilePicture: String?,
)

data class RemoteRegisterRequestBody(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: Int,
)

data class RemoteLoginRequestBody(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: Int,
)

data class RemoteAuthResponse(
    @SerializedName("\$id") val _id: String,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: RemoteAuthResponseData?
)

data class RemoteAuthResponseData(
    @SerializedName("\$id") val _id: String,
    @SerializedName("Id") val id: Int,
    @SerializedName("Name") val name: String,
    @SerializedName("Email") val email: String,
    @SerializedName("Token") val token: String,
)