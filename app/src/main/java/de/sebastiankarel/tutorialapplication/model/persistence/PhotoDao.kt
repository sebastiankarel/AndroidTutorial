package de.sebastiankarel.tutorialapplication.model.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import de.sebastiankarel.tutorialapplication.model.Photo

@Dao
interface PhotoDao {

    @Insert
    suspend fun addPhotos(vararg photos: Photo): List<Long>

    @Query("SELECT * FROM photos WHERE id = :id")
    suspend fun getPhotoById(id: Long): Photo

    @Query("DELETE FROM photos WHERE id = :id")
    suspend fun deletePhotoById(id: Long)

    @Query("DELETE FROM photos WHERE id NOT IN (SELECT id FROM users)")
    suspend fun deleteSpuriousPhotos()
}