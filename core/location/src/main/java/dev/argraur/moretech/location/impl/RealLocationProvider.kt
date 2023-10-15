package dev.argraur.moretech.location.impl

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dev.argraur.moretech.activitycore.ActivityProvider
import dev.argraur.moretech.location.LocationProvider
import dev.argraur.moretech.location.exceptions.NoPermissionException
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class RealLocationProvider @Inject constructor(
    private val activityProvider: ActivityProvider
) : LocationProvider {
    private var fusedLocationClient: FusedLocationProviderClient? = null

    override suspend fun getLocationLatLng(): Pair<Double, Double>? {
        if (ActivityCompat.checkSelfPermission(
                activityProvider.activity!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activityProvider.activity!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            launchLocationPermissionPrompt()
            return null
        }

        if (fusedLocationClient == null)
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(activityProvider.activity!!)

        return suspendCancellableCoroutine { continuation ->
            fusedLocationClient!!.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    continuation.resume(Pair(location.latitude, location.longitude))
                } else {
                    continuation.resume(null)
                }
            }.addOnFailureListener {
                continuation.resume(null) // Resume with null or handle the error appropriately
            }
        }
    }

    private fun launchLocationPermissionPrompt() {
        val locationPermissionRequest = (activityProvider.activity as ComponentActivity).registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions -> when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {

            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {

            } else -> {
                Log.e(this::class.simpleName, "Failed to retrieve location permission")
            }
        }}

        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ))
    }
}