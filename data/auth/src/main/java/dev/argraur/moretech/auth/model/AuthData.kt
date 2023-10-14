package dev.argraur.moretech.auth.model

import dev.argraur.moretech.database.model.AuthDataEntity
import dev.argraur.moretech.network.model.NetworkAuthData

data class AuthData(
    val phoneNumber: String,
    val token: String
)

fun AuthDataEntity.toRegular() = AuthData(this.phoneNumber, this.token)
fun NetworkAuthData.toRegular(phoneNumber: String) = AuthData(phoneNumber, this.token)