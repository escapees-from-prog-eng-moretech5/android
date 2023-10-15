package dev.argraur.moretech.map.ui.marker

import androidx.compose.runtime.Composable
import dev.argraur.moretech.map.ui.MapViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import dev.argraur.moretech.offices.model.atm.Atm

@Composable
fun AtmMarker(atm: Atm, viewModel: MapViewModel = viewModel()) {
    Marker(
        state = MarkerState(position = LatLng(atm.latitude, atm.longitude)),
        title = atm.address,
        snippet = "Marker in Moscow",
        onClick = {
            true
        }
    )
}