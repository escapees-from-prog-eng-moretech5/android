package dev.argraur.moretech.network

import android.util.Log
import dev.argraur.moretech.network.retrofit.AtmService
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AtmNetworkDataSource @Inject constructor(
    private val atmService: AtmService
) {
    val atms = flow {
        Log.i("AtmNetworkDataSource", "Retrieving atms...")
        val remoteAtms = atmService.getAtms()
        emit(remoteAtms)
    }.catch {
        Log.i("ATMS", "${it.message}\n${it.stackTrace}")
    }
}