package dev.argraur.moretech.android.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.argraur.moretech.android.ui.navigation.MORETechNavHost
import kotlinx.coroutines.CoroutineScope

@Composable
fun MORETechApp(coroutineScope: CoroutineScope, startDestination: String, viewModel: MORETechAppViewModel = MORETechAppViewModel()) {
    MORETechNavHost(
        coroutineScope = coroutineScope,
        modifier = Modifier.fillMaxSize(),
        startDestination = startDestination
    )
}
