package dev.argraur.moretech.auth.model

import dev.argraur.moretech.database.model.auth.AuthDataEntity
import dev.argraur.moretech.database.model.auth.UserEntity
import dev.argraur.moretech.network.model.LoginResponse
import dev.argraur.moretech.network.model.RegisterResponse

data class AuthData(
    val token: String,
    val user: User
) {
    fun toEntity(): AuthDataEntity {
        return AuthDataEntity(0, token, UserEntity(user.number, user.name))
    }
}
fun AuthDataEntity.toRegular() = AuthData(token, user.toRegular())
fun RegisterResponse.toRegular() = AuthData(token, user.toRegular())
fun LoginResponse.toRegular() = AuthData(token, User("",""))