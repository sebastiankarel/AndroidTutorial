package de.sebastiankarel.tutorialapplication.model

object ApiModelMapper {

    fun mapUser(remoteUser: RemoteUser, photoId: Long): User {
       return User(
            name = remoteUser.name ?: "",
            email = remoteUser.email ?: "",
            photoId = photoId,
            timeStamp = System.currentTimeMillis()
        )
    }

    fun mapPhoto(remoteUser: RemoteUser): Photo {
        return Photo(imageUrl = remoteUser.profilePicture)
    }
}