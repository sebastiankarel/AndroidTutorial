package de.sebastiankarel.tutorialapplication.model

import de.sebastiankarel.tutorialapplication.model.persistence.UserDao
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(
    private val remoteUserService: RemoteUserService,
    private val userDao: UserDao
) : Repository {

    override fun users(): Flow<List<User>> = userDao.getAll()

    override suspend fun fetchUsers(numUsers: Int) {
        val remoteUsers = remoteUserService.getUsers(numUsers)
        val mapped = remoteUsers.results.map { ApiModelMapper.mapUser(it) }.toTypedArray()
        userDao.addUsers(*mapped)
    }

    override suspend fun getAllIds(): List<Int> {
        return userDao.getAllIds()
    }

    override suspend fun addUser(user: User) {
        userDao.addUsers(user)
    }

    override suspend fun getUserById(id: Int): User {
        return userDao.getUserById(id)
    }

    override suspend fun deleteUser(id: Int) {
        userDao.deleteUserById(id)
    }
}