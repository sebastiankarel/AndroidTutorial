package de.sebastiankarel.tutorialapplication.model

import kotlinx.coroutines.flow.Flow

interface Repository {

    fun users(): Flow<List<User>>

    suspend fun getAllIds(): List<Int>

    suspend fun fetchUsers(numUsers: Int)

    suspend fun addUser(user: User)

    suspend fun getUserById(id: Int): User

    suspend fun deleteUser(id: Int)
}