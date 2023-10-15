package dev.argraur.moretech.network

import android.util.Log
import dev.argraur.moretech.network.retrofit.OfficeService
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfficeNetworkDataSource @Inject constructor(
    private val officeService: OfficeService
) {
    val offices = flow {
        Log.i("OfficeNetworkDataSource", "Getting offices...")
        val remoteOffices = officeService.getOffices()
        Log.i("OfficeNetworkDataSource", "Offices: $remoteOffices")
        emit(remoteOffices)
    }.catch { exception -> Log.i("EXCEPTION", "${exception.message}") }
}