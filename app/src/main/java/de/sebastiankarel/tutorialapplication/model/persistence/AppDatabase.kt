package de.sebastiankarel.tutorialapplication.model.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import de.sebastiankarel.tutorialapplication.model.Photo
import de.sebastiankarel.tutorialapplication.model.User

@Database(entities = [User::class, Photo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun getPhotoDao(): PhotoDao
}