package dev.argraur.moretech.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.argraur.moretech.database.dao.AuthDao
import dev.argraur.moretech.database.dao.MapDao
import dev.argraur.moretech.database.model.atm.AtmEntity
import dev.argraur.moretech.database.model.auth.AuthDataEntity
import dev.argraur.moretech.database.model.office.OfficeEntity
import dev.argraur.moretech.database.utils.Converter

@Database(
    entities = [AuthDataEntity::class, AtmEntity::class, OfficeEntity::class],
    version = 3
)
@TypeConverters(Converter::class)
abstract class MORETechDatabase : RoomDatabase() {
    abstract fun authDao() : AuthDao
    abstract fun mapDao(): MapDao
}