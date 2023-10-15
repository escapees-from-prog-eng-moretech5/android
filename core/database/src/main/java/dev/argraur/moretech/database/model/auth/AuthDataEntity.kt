package dev.argraur.moretech.database.model.auth

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "auth")
data class AuthDataEntity (
    @PrimaryKey
    val id: Int,
    val token: String,
    @Embedded val user: UserEntity
)