package dev.argraur.moretech.auth.model

import dev.argraur.moretech.database.model.auth.UserEntity
import dev.argraur.moretech.network.model.NetworkUser

data class User(
    val number: String,
    val name: String
)

fun NetworkUser.toRegular() = User(number, name)
fun UserEntity.toRegular() = User(number, name)