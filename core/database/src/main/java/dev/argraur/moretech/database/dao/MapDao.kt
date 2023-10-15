package dev.argraur.moretech.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.argraur.moretech.database.model.atm.AtmEntity
import dev.argraur.moretech.database.model.office.OfficeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MapDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAtms(vararg atms: AtmEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllOffices(vararg offices: OfficeEntity)

    @Query("SELECT * FROM atms")
    fun getAllAtms(): Flow<List<AtmEntity>>

    @Query("SELECT * FROM offices")
    fun getAllOffices(): Flow<List<OfficeEntity>>
}