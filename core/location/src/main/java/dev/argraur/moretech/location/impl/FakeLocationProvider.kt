package dev.argraur.moretech.location.impl

import dev.argraur.moretech.location.LocationProvider

class FakeLocationProvider : LocationProvider {
    override suspend fun getLocationLatLng(): Pair<Double, Double>? {
        return Pair(55.751244, 37.618423)
    }
}