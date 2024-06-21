package fr.agrouagrou.mobileapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.agrouagrou.mobileapp.ui.common.GameViewModel
import fr.agrouagrou.mobileapp.ui.createjoingame.CreateGameRoute
import fr.agrouagrou.mobileapp.ui.createjoingame.HomeRoute
import fr.agrouagrou.mobileapp.ui.createjoingame.JoinGameRoute

object Destinations {
    const val HOME = "home"
    const val CREATE_GAME = "create-game"
    const val JOIN_GAME = "join-game"
    const val GAME_PHASE = "game-phase"
}

@Composable
fun AgrouAgrouNavHost(
    navController: NavHostController = rememberNavController(),
) {
    val gameViewModel = GameViewModel()
    val gameUiState by gameViewModel.gameManager.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Destinations.HOME
    ) {
        composable(Destinations.HOME) {
            HomeRoute(
                onCreateGame = {
                    gameViewModel.createGame()
                    navController.navigate(Destinations.CREATE_GAME);
                },
                onJoinGame = { navController.navigate(Destinations.JOIN_GAME) }
            )
        }

        composable(Destinations.CREATE_GAME) {
            CreateGameRoute()
        }

        composable(Destinations.JOIN_GAME) {
            JoinGameRoute()
        }

        composable(Destinations.GAME_PHASE) {
            // GamePhaseRoute()
        }
    }
}
