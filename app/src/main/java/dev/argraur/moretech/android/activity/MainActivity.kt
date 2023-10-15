package dev.argraur.moretech.android.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import dev.argraur.moretech.activitycore.ActivityProvider
import dev.argraur.moretech.android.ui.MORETechApp
import dev.argraur.moretech.android.ui.navigation.MORETechNavHost
import dev.argraur.moretech.android.ui.theme.MORETechVTBMapTheme
import dev.argraur.moretech.auth.ui.LoginScreen
import dev.argraur.moretech.network.token.TokenProvider
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var activityProvider: ActivityProvider
    @Inject lateinit var tokenProvider: TokenProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityProvider.attachActivity(this)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {
            MORETechVTBMapTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MORETechApp(coroutineScope = lifecycleScope, startDestination = if (tokenProvider.getToken() == null) "login" else "map")
                }
            }
        }
    }

    override fun onDestroy() {
        activityProvider.detachActivity()
        super.onDestroy()
    }
}