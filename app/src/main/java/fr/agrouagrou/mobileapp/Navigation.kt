package fr.agrouagrou.mobileapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.agrouagrou.mobileapp.ui.createjoingame.CreateGameRoute
import fr.agrouagrou.mobileapp.ui.createjoingame.HomeRoute
import fr.agrouagrou.mobileapp.ui.createjoingame.JoinGameRoute

object Destinations {
    const val HOME = "home"
    const val CREATE_GAME = "create-game"
    const val JOIN_GAME = "join-game"
}

@Composable
fun AgrouAgrouNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.HOME
    ) {
        composable(Destinations.HOME) {
            HomeRoute(
                onCreateGame = { navController.navigate(Destinations.CREATE_GAME) },
                onJoinGame = { navController.navigate(Destinations.JOIN_GAME) }
            )
        }

        composable(Destinations.CREATE_GAME) {
            CreateGameRoute()
        }

        composable(Destinations.JOIN_GAME) {
            JoinGameRoute()
        }
    }
}
