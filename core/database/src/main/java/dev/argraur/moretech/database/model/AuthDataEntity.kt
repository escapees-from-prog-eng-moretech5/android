package dev.argraur.moretech.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "auth")
data class AuthDataEntity (
    @PrimaryKey
    val id: Int,
    val phoneNumber: String,
    val token: String
)