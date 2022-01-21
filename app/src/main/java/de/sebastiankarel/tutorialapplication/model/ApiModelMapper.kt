package de.sebastiankarel.tutorialapplication.model

object ApiModelMapper {

    fun mapUser(remoteUser: RemoteUser): User {
        return User(
            name = "${remoteUser.name.firstName} ${remoteUser.name.lastName}",
            email = remoteUser.email,
            thumbUrl = remoteUser.picture.thumbnail,
            imageUrl = remoteUser.picture.large,
            timeStamp = System.currentTimeMillis()
        )
    }
}