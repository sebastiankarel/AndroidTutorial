package de.sebastiankarel.tutorialapplication.model

import de.sebastiankarel.tutorialapplication.model.persistence.PhotoDao
import de.sebastiankarel.tutorialapplication.model.persistence.UserDao
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(
    private val remoteUserService: RemoteUserService,
    private val userDao: UserDao,
    private val photoDao: PhotoDao
) : Repository {

    override fun users(): Flow<List<User>> = userDao.getAll()

    override suspend fun fetchUsers(numUsers: Int) {
        val remoteUsers = remoteUserService.getUsers(numUsers).results
        val photos = remoteUsers.map { ApiModelMapper.mapPhoto(it) }.toTypedArray()
        val photoIds = photoDao.addPhotos(*photos)
        val users = remoteUsers.mapIndexed { idx, user -> ApiModelMapper.mapUser(user, photoIds[idx]) }.toTypedArray()
        userDao.addUsers(*users)
    }

    override suspend fun getAllIds(): List<Int> {
        return userDao.getAllIds()
    }

    override suspend fun addPhoto(imageData: ByteArray): Long {
        val photo = Photo(imageData = imageData)
        return photoDao.addPhotos(photo)[0]
    }

    override suspend fun getPhotoById(photoId: Long): Photo? {
        if (photoId < 0) return null
        return photoDao.getPhotoById(photoId)
    }

    override suspend fun deletePhotoById(photoId: Long) {
        if (photoId < 0) return
        photoDao.deletePhotoById(photoId)
    }

    override suspend fun addUser(name: String, email: String, photoId: Long) {
        val user = User(name = name, email = email, photoId = photoId, timeStamp = System.currentTimeMillis())
        userDao.addUsers(user)
    }

    override suspend fun getUserById(id: Int): User {
        return userDao.getUserById(id)
    }

    override suspend fun deleteUser(id: Int) {
        val photoId = userDao.getPhotoIdForUserId(id)
        photoDao.deletePhotoById(photoId)
        userDao.deleteUserById(id)
    }

    override suspend fun clearSpuriousImages() = photoDao.deleteSpuriousPhotos()
}