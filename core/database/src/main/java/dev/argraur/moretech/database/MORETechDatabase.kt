package dev.argraur.moretech.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.argraur.moretech.database.dao.AuthDao
import dev.argraur.moretech.database.model.AuthDataEntity

@Database(entities = [AuthDataEntity::class], version = 1)
abstract class MORETechDatabase : RoomDatabase() {
    abstract fun authDao() : AuthDao
}