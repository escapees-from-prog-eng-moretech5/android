package dev.argraur.moretech.android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.Polyline
import com.google.maps.model.TravelMode
import dev.argraur.moretech.android.utils.toApiLatLng
import dev.argraur.moretech.android.utils.toGmsLatLng

@Composable
fun MORETechApp(coroutineScope: CoroutineScope, viewModel: MORETechAppViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val moscow = LatLng(55.751244, 37.618423)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(moscow, 12f)
    }
    val uiSettings by remember { mutableStateOf(MapUiSettings(zoomControlsEnabled = false)) }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = uiSettings
        ) {
            for (p in uiState.places) {
                Marker(
                    state = MarkerState(position = p.toGmsLatLng()),
                    title = "point",
                    snippet = "Marker in Moscow",
                    onClick = {
                        viewModel.checkMarker(p)
                        true
                    }
                )
            }
            for (r in uiState.routes) {
                Polyline(
                    points = r.overviewPolyline.decodePath().map { it.toGmsLatLng() },
                    color = Color.Blue,
                    visible = true
                )
                LaunchedEffect(uiState) {
                    val boundsBuilder = LatLngBounds.builder()
                    r.overviewPolyline.decodePath().map { boundsBuilder.include(it.toGmsLatLng()) }
                    cameraPositionState.animate(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 50), 500)
                }
            }
        }
        ConstraintLayout(modifier = Modifier
            .matchParentSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .background(Color.Transparent)) {
            val (fab, fabL, fabM, fabC) = createRefs()
            val text = createRef()
            Text(modifier = Modifier
                .padding(24.dp)
                .constrainAs(text) {
                    top.to(parent.top)
                    start.to(parent.start)
                    end.to(parent.end)
                }, text = if (uiState.marker != null) "LOC: ${uiState.marker}" else "", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            FloatingActionButton(
                modifier = Modifier
                    .constrainAs(fab) {
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(12.dp, 3.dp, 12.dp, 12.dp),
                onClick = {
                    coroutineScope.launch(Dispatchers.Main) {
                        cameraPositionState.animate(CameraUpdateFactory.zoomOut(), 200)
                    }
                    viewModel.addPlace(moscow.toApiLatLng())
                }) {
                Icon(Icons.Filled.KeyboardArrowDown, "Zoom In")
            }
            FloatingActionButton(
                modifier = Modifier
                    .constrainAs(fabL) {
                        end.linkTo(parent.end)
                        bottom.linkTo(fab.top)
                    }
                    .padding(12.dp, 12.dp, 12.dp, 3.dp),
                onClick = {
                    coroutineScope?.launch(Dispatchers.Main) {
                        cameraPositionState.animate(CameraUpdateFactory.zoomIn(), 200)
                    }
                }) {
                Icon(Icons.Filled.KeyboardArrowUp, "Zoom In")
            }
            FloatingActionButton(
                modifier = Modifier
                    .constrainAs(fabM) {
                        end.linkTo(parent.end)
                        bottom.linkTo(fabL.top)
                    }
                    .padding(12.dp, 12.dp, 12.dp, 3.dp),
                onClick = {
                    viewModel.getRoute(uiState.places[0], uiState.places[1], TravelMode.DRIVING)
                }) {
                Icon(Icons.Filled.Done, "Zoom In")
            }
            FloatingActionButton(
                modifier = Modifier
                    .constrainAs(fabC) {
                        end.linkTo(parent.end)
                        bottom.linkTo(fabM.top)
                    }
                    .padding(12.dp, 12.dp, 12.dp, 3.dp),
                onClick = {
                    viewModel.clearRoute()
                }) {
                Icon(Icons.Filled.Close, "Zoom In")
            }
        }
    }
}
