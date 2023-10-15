package dev.argraur.moretech.network.retrofit

import dev.argraur.moretech.network.model.NetworkAtm
import retrofit2.http.GET

interface AtmService {
    @GET("api/v1/atm")
    suspend fun getAtms(): List<NetworkAtm>
}