package dev.argraur.moretech.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.argraur.moretech.auth.ui.LoginScreen
import dev.argraur.moretech.auth.ui.RegisterScreen
import dev.argraur.moretech.map.ui.MapScreen
import kotlinx.coroutines.CoroutineScope

@Composable
fun MORETechNavHost(
    coroutineScope: CoroutineScope,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "login"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("login") {
            LoginScreen(
                onNavigateToRegister = { navController.navigate("register") },
                onNavigateToMap = { navController.navigate("map") },
                viewModel = hiltViewModel()
            )
        }
        composable("register") {
            RegisterScreen(
                onNavigateToLogin = { navController.navigate("login") },
                onNavigateToMap = { navController.navigate("map") },
                viewModel = hiltViewModel()
            )
        }
        composable("map") {
            MapScreen(
                modifier = modifier,
                coroutineScope = coroutineScope,
                viewModel = hiltViewModel()
            )
        }
    }
}