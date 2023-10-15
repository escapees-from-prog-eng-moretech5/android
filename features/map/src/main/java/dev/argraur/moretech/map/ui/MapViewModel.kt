package dev.argraur.moretech.map.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.google.maps.model.DirectionsRoute
import com.google.maps.model.LatLng
import com.google.maps.model.TravelMode
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.argraur.moretech.map.di.annotations.DataSet
import dev.argraur.moretech.location.exceptions.NoPermissionException
import dev.argraur.moretech.location.LocationProvider
import dev.argraur.moretech.location.di.annotations.FakeProvider
import dev.argraur.moretech.map.items.AtmItem
import dev.argraur.moretech.map.items.OfficeItem
import dev.argraur.moretech.offices.model.atm.Atm
import dev.argraur.moretech.offices.model.office.Office
import dev.argraur.moretech.offices.repository.MapRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class MapUiState(
    val atms: List<Atm> = listOf(),
    val offices: List<Office> = listOf(),
    val routes: List<DirectionsRoute> = listOf(),
    val showOfficeSheet: Boolean = false,
    val showAtmSheet: Boolean = false,
    val sheetUiState: ModalBottomSheetUiState = ModalBottomSheetUiState(),
    val marker: LatLng? = null,
    val location: LatLng? = null,
    val showATM: Boolean = true,
    val showOffices: Boolean = true,
    val disableClusterization: Boolean = true
)

data class ModalBottomSheetUiState(
    val atm: Atm? = null,
    val office: Office? = null
)

@HiltViewModel
class MapViewModel @Inject constructor(
    private val geoApiContext: GeoApiContext,
    @FakeProvider private val locationProvider: LocationProvider,
    private val mapRepository: MapRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getLocation()
            withContext(Dispatchers.IO) {
                mapRepository.atms.collect { atms ->
                    Log.i("ATMS", "GOT ATMS!! ${atms}")
                    _uiState.update {
                        it.copy(atms = atms)
                    }
                }
            }
            withContext(Dispatchers.IO) {
                mapRepository.offices.collect { offices ->
                    Log.i("OFFICES", "GOT OFFICES!! ${offices}")
                    _uiState.update {
                        it.copy(offices = offices)
                    }
                }
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
                    it.copy(routes = listOf(result.routes[0]))
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

    fun getLocation() : Boolean {
        try {
            viewModelScope.launch {
                val location = locationProvider.getLocationLatLng() ?: return@launch
                _uiState.update {
                    it.copy(location = LatLng(location.first, location.second))
                }
            }
        } catch (exception: NoPermissionException) {
            return false
        }
        return true
    }

    fun showOfficeSheet(officeItem: OfficeItem) {
        _uiState.update {
            it.copy(
                showOfficeSheet = true,
                sheetUiState = ModalBottomSheetUiState(office = officeItem.office)
            )
        }
    }

    fun showAtmSheet(atmItem: AtmItem) {
        _uiState.update {
            it.copy(
                showAtmSheet = true,
                sheetUiState = ModalBottomSheetUiState(atm = atmItem.atm)
            )
        }
    }

    fun dismissSheet() {
        _uiState.update {
            it.copy(
                showOfficeSheet = false,
                showAtmSheet = false,
                sheetUiState = ModalBottomSheetUiState()
            )
        }
    }

    fun toggleATM() {
        _uiState.update {
            it.copy(showATM = !it.showATM)
        }
    }

    fun toggleOffices() {
        _uiState.update {
            it.copy(showOffices = !it.showOffices)
        }
    }
}