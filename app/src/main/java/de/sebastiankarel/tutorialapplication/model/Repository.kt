package de.sebastiankarel.tutorialapplication.model

import kotlinx.coroutines.flow.Flow

interface Repository {

    fun users(): Flow<List<User>>

    suspend fun getAllIds(): List<Int>

    suspend fun fetchUsers(numUsers: Int)

    suspend fun addPhoto(imageData: ByteArray): Long

    suspend fun getPhotoById(photoId: Long): Photo?

    suspend fun deletePhotoById(photoId: Long)

    suspend fun addUser(name: String, email: String, photoId: Long)

    suspend fun getUserById(id: Int): User

    suspend fun deleteUser(id: Int)

    suspend fun clearSpuriousImages()
}