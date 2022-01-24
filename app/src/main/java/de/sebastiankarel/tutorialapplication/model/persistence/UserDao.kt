package de.sebastiankarel.tutorialapplication.model.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import de.sebastiankarel.tutorialapplication.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM users ORDER BY name ASC")
    fun getAll(): Flow<List<User>>

    @Query("SELECT id FROM users")
    fun getAllIds(): List<Int>

    @Insert
    suspend fun addUsers(vararg users: User)

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Int): User

    @Query("DELETE FROM users WHERE id = :id")
    suspend fun deleteUserById(id: Int)

    @Query("SELECT photo_id FROM users WHERE id = :id")
    suspend fun getPhotoIdForUserId(id: Int): Long
}