package dev.argraur.moretech.offices.repository

import android.util.Log
import dev.argraur.moretech.database.dao.MapDao
import dev.argraur.moretech.network.AtmNetworkDataSource
import dev.argraur.moretech.network.OfficeNetworkDataSource
import dev.argraur.moretech.offices.model.atm.Atm
import dev.argraur.moretech.offices.model.atm.toAtm
import dev.argraur.moretech.offices.model.office.Office
import dev.argraur.moretech.offices.model.office.toOffice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MapRepository @Inject constructor(
    private val atmNetworkDataSource: AtmNetworkDataSource,
    private val officeNetworkDataSource: OfficeNetworkDataSource,
    private val mapDao: MapDao
) {
    val atms: Flow<List<Atm>> =
        atmNetworkDataSource.atms
            .onEach {
                mapDao.insertAllAtms(*it.map { it.toAtm().toEntity() }.toTypedArray())
            }
            .map { it.map { it.toAtm() } }
            .catch { exception ->
                mapDao.getAllAtms().collect {
                    emit(it.map { it.toAtm() })
                }
            }

    val offices: Flow<List<Office>> =
        officeNetworkDataSource.offices
            .onEach {
                Log.i("MapRepository","I got offices too! ${it}")
                mapDao.insertAllOffices(*it.map { it.toOffice().toEntity() }.toTypedArray())
            }
            .map { it.map { it.toOffice() } }
            .catch { exception ->
                Log.i("MapRepository", "Exception: ${exception.message}\n${exception.stackTrace}")
                mapDao.getAllOffices().collect {
                    emit(it.map { it.toOffice() })
                }
            }
}