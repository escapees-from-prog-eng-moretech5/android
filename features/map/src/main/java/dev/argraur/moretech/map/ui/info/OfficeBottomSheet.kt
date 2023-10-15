package dev.argraur.moretech.map.ui.info

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import dev.argraur.moretech.map.R
import dev.argraur.moretech.map.ui.ModalBottomSheetUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfficeBottomSheet(
    uiState: ModalBottomSheetUiState,
    sheetState: SheetState,
    onCalculateRoute: () -> Unit,
    onDismiss: () -> Unit
)  {
    ModalBottomSheet(
        modifier = Modifier.fillMaxSize(),
        onDismissRequest = onDismiss,
        scrimColor = Color.Transparent,
        sheetState = sheetState,
        windowInsets = WindowInsets.safeDrawing
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (title, routeFab) = createRefs()
            Text(
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(16.dp),
                text = uiState.office?.address!!
            )

            ExtendedFloatingActionButton(
                modifier = Modifier.constrainAs(routeFab) {
                    top.linkTo(title.bottom)
                    end.linkTo(parent.end)
                }.padding(0.dp, 12.dp, 12.dp, 0.dp),
                text = { Text("Маршрут") },
                icon = { Icon(painterResource(id = R.drawable.route), contentDescription = "Route") },
                onClick = onCalculateRoute)
        }
    }
}