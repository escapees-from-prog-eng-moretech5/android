package dev.argraur.moretech.android.utils

import com.google.maps.model.LatLng

fun LatLng.toGmsLatLng(): com.google.android.gms.maps.model.LatLng
    = com.google.android.gms.maps.model.LatLng(this.lat, this.lng)

fun com.google.android.gms.maps.model.LatLng.toApiLatLng(): LatLng
    = LatLng(this.latitude, this.longitude)