package de.sebastiankarel.tutorialapplication.model

object ApiModelMapper {

    fun mapUser(remoteUser: RemoteUser): User {
        return User(
            "${remoteUser.name.title} ${remoteUser.name.firstName} ${remoteUser.name.lastName}",
            remoteUser.email,
            remoteUser.picture.thumbnail
        )
    }
}