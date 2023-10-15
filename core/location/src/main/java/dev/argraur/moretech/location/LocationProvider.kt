package dev.argraur.moretech.location

interface LocationProvider {
    suspend fun getLocationLatLng(): Pair<Double, Double>?
}