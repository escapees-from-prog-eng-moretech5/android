package dev.argraur.moretech.map.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.clustering.Clustering
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.model.TravelMode
import dev.argraur.moretech.map.R
import dev.argraur.moretech.map.items.AtmItem
import dev.argraur.moretech.map.items.OfficeItem
import dev.argraur.moretech.map.ui.info.AtmBottomSheet
import dev.argraur.moretech.map.ui.info.OfficeBottomSheet
import dev.argraur.moretech.map.utils.bitmapDescriptor
import dev.argraur.moretech.map.utils.toGmsLatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, MapsComposeExperimentalApi::class)
@Composable
fun MapScreen(modifier: Modifier, coroutineScope: CoroutineScope, viewModel: MapViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val moscow = LatLng(55.751244, 37.618423)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(moscow, 12f)
    }
    val uiSettings by remember { mutableStateOf(MapUiSettings(zoomControlsEnabled = false)) }
    val sheetState = rememberModalBottomSheetState()
    val showBottomSheet by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = uiSettings
        ) {
            if (!uiState.disableClusterization) {
                if (uiState.showATM)
                    Clustering(items = uiState.atms.map { AtmItem(it) })

                if (uiState.showOffices)
                    Clustering(
                        items = uiState.offices.map { OfficeItem(it) },
                        clusterItemContent = {
                            Image(painterResource(id = R.drawable.vtb_marker), "Office marker")
                        },
                        onClusterItemClick = { viewModel.showOfficeSheet(it); true })
            } else {
                if (uiState.showATM) {
                    for (atm in uiState.atms)
                        Marker(
                            state = MarkerState(position = LatLng(atm.latitude, atm.longitude)),
                            icon = bitmapDescriptor(LocalContext.current, R.drawable.atm_vtb_marker),
                            onClick = { viewModel.showAtmSheet(AtmItem(atm)); true}
                        )
                }
                if (uiState.showOffices) {
                    for (office in uiState.offices) {
                        Marker(
                            state = MarkerState(position = LatLng(office.latitude, office.longitude)),
                            icon = bitmapDescriptor(LocalContext.current, R.drawable.vtb_marker),
                            onClick = {
                                val boundsBuilder = LatLngBounds.builder()
                                boundsBuilder.include(uiState.location!!.toGmsLatLng())
                                boundsBuilder.include(LatLng(office.latitude, office.longitude))
                                coroutineScope.launch(Dispatchers.Main) {
                                    cameraPositionState.animate(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(),200))
                                }
                                viewModel.showOfficeSheet(OfficeItem(office)); true
                            }
                        )
                    }
                }
            }

            if (uiState.location != null) {
                Marker(
                    state = MarkerState(uiState.location!!.toGmsLatLng()),
                    icon = bitmapDescriptor(LocalContext.current, R.drawable.location)
                )
            }

            for (r in uiState.routes) {
                Polyline(
                    points = r.overviewPolyline.decodePath().map { it.toGmsLatLng() },
                    color = Color.Blue,
                    visible = true
                )
            }
        }

        ConstraintLayout(modifier = Modifier
            .matchParentSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .background(Color.Transparent)) {
            val (fab, fabL, fabM, fabC, zoomRow) = createRefs()
            val text = createRef()
            Text(modifier = Modifier
                .padding(24.dp)
                .constrainAs(text) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, text = if (uiState.marker != null) "LOC: ${uiState.marker}" else "", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Column(
                modifier = Modifier.constrainAs(zoomRow) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
            ) {
                FloatingActionButton(
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 12.dp, 3.dp),
                    onClick = {
                        coroutineScope.launch(Dispatchers.Main) {
                            cameraPositionState.animate(CameraUpdateFactory.zoomIn(), 200)
                        }
                    }) {
                    Icon(painterResource(id = R.drawable.zoom_in), "Zoom In")
                }
                FloatingActionButton(
                    modifier = Modifier
                        .padding(0.dp, 3.dp, 12.dp, 0.dp),
                    onClick = {
                        coroutineScope.launch(Dispatchers.Main) {
                            cameraPositionState.animate(CameraUpdateFactory.zoomOut(), 200)
                        }
                    }) {
                    Icon(painterResource(id = R.drawable.zoom_out), "Zoom Out")
                }
            }

            FloatingActionButton(
                modifier = Modifier
                    .constrainAs(fabM) {
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(12.dp, 12.dp, 12.dp, 3.dp),
                onClick = {
                    viewModel.toggleATM()
                }) {
                Icon(painterResource(id = R.drawable.atm_btn), "ATM Trigger")
            }

            FloatingActionButton(
                modifier = Modifier
                    .constrainAs(fabC) {
                        end.linkTo(parent.end)
                        bottom.linkTo(fabM.top)
                    }
                    .padding(12.dp, 12.dp, 12.dp, 3.dp),
                onClick = {
                    viewModel.toggleOffices()
                }) {
                Icon(painterResource(id = R.drawable.bank_btn), "Bank Trigger")
            }
            val fabLOC = createRef()
            FloatingActionButton(
                modifier = Modifier
                    .constrainAs(fabLOC) {
                        end.linkTo(parent.end)
                        bottom.linkTo(fabC.top)
                    }
                    .padding(12.dp, 12.dp, 12.dp, 3.dp),
                onClick = {
                    if (uiState.location != null)
                        coroutineScope.launch(Dispatchers.Main) {
                            cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(uiState.location!!.toGmsLatLng(), 18f), 500)
                        }
                }
            ) {
                Icon(Icons.Filled.LocationOn, contentDescription = "Location")
            }
        }

        if (uiState.showATM) {
            AtmBottomSheet(uiState.sheetUiState, {})
        }

        if (uiState.showOfficeSheet) {
            OfficeBottomSheet(
                uiState.sheetUiState,
                sheetState,
                {
                    viewModel.getRoute(
                        uiState.location!!,
                        com.google.maps.model.LatLng(uiState.sheetUiState.office!!.latitude, uiState.sheetUiState.office!!.longitude),
                        TravelMode.DRIVING
                    )
                },
                { viewModel.dismissSheet() }
            )
        }
    }
}