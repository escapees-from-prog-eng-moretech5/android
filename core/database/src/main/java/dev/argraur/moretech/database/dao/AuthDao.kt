package dev.argraur.moretech.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.argraur.moretech.database.model.AuthDataEntity

@Dao
interface AuthDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(authDataEntity: AuthDataEntity)

    @Delete
    fun delete(authDataEntity: AuthDataEntity)

    @Query("SELECT * FROM auth")
    fun loadAuthData(): List<AuthDataEntity>
}