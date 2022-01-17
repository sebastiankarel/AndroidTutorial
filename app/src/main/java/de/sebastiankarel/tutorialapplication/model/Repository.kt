package de.sebastiankarel.tutorialapplication.model

class Repository(private val remoteUserService: RemoteUserService) {

    suspend fun getItems(): List<User> {
        val result = mutableListOf<User>()
        val response = remoteUserService.getUsers(20)
        response.results.forEach {
            result.add(ApiModelMapper.mapUser(it))
        }
        return result
    }
}