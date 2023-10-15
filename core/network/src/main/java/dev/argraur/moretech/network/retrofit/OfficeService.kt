package dev.argraur.moretech.network.retrofit

import dev.argraur.moretech.network.model.NetworkOffice
import retrofit2.http.GET

interface OfficeService {
    @GET("api/v1/office")
    suspend fun getOffices(): List<NetworkOffice>
}