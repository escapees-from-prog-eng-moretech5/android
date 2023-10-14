package dev.argraur.moretech.android.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.Marker
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.google.maps.model.DirectionsRoute
import com.google.maps.model.LatLng
import com.google.maps.model.TravelMode
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.argraur.moretech.android.di.annotations.DataSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MapUiState(
    val places: List<LatLng> = listOf(),
    val routes: List<DirectionsRoute> = listOf(),
    val marker: LatLng? = null
)

@HiltViewModel
class MORETechAppViewModel @Inject constructor(
    private val geoApiContext: GeoApiContext,
    @DataSet private val places: List<@JvmSuppressWildcards LatLng>
) : ViewModel() {
    private val _uiState = MutableStateFlow(MapUiState(places = places))
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    fun addPlace(place: LatLng) {
        _uiState.update {
            if (!it.places.contains(place)) {
                it.copy(places = it.places + place)
            } else {
                it
            }
        }
    }

    fun getRoute(a: LatLng, b: LatLng, mode: TravelMode) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = DirectionsApi.newRequest(geoApiContext)
                .origin(a)
                .destination(b)
                .mode(mode)
                .await()

            if (result != null) {
                _uiState.update {
                    it.copy(routes = it.routes + result.routes[0])
                }
            }
        }
    }

    fun clearRoute() {
        viewModelScope.launch {
            _uiState.update { it.copy(routes = listOf()) }
        }
    }

    fun checkMarker(marker: LatLng) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(marker = marker)
            }
        }
    }
}