package dev.argraur.moretech.android.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import dev.argraur.moretech.map.ui.MapScreen
import kotlinx.coroutines.CoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.argraur.moretech.android.ui.navigation.MORETechNavHost

@Composable
fun MORETechApp(coroutineScope: CoroutineScope, startDestination: String, viewModel: MORETechAppViewModel = MORETechAppViewModel()) {
    MORETechNavHost(
        coroutineScope = coroutineScope,
        modifier = Modifier.fillMaxSize(),
        startDestination = startDestination
    )
}
