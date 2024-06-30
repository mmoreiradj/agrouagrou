package fr.agrouagrou.mobileapp

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.agrouagrou.grpc_server.BuildConfig
import fr.agrouagrou.mobileapp.ui.common.ClientGameViewModel
import fr.agrouagrou.mobileapp.ui.common.ClientPlayerViewModel
import fr.agrouagrou.mobileapp.ui.common.GameViewModel
import fr.agrouagrou.mobileapp.ui.createjoingame.CreateGameRoute
import fr.agrouagrou.mobileapp.ui.createjoingame.HomeRoute
import fr.agrouagrou.mobileapp.ui.createjoingame.JoinGameRoute
import fr.agrouagrou.mobileapp.ui.gamephases.GamePhaseGameMasterRoute
import fr.agrouagrou.mobileapp.ui.gameroutes.GameRunningRoute
import io.grpc.ManagedChannelBuilder

object Destinations {
    const val HOME = "home"
    const val CREATE_GAME = "create-game"
    const val JOIN_GAME = "join-game"
    const val CLIENT_GAME_PHASE = "game-phase"
    const val GAME_PHASE_GAMEMASTER = "game-phase-gamemaster"
}

fun handleJoinGame(gameCode: String, navController: NavHostController) {
    val ipPort = if (BuildConfig.DEBUG) {
        Pair("10.0.2.2", 50051)
    } else {
        IpPortEncDec.decodeIpPort(gameCode)
    }
    Log.d("JoinGameRoute", "Decoded IP and port: $ipPort")
    if (ipPort != null) {
        navController.navigate(Destinations.CLIENT_GAME_PHASE)
    } else {
        // TODO: Handle error
        Log.e("JoinGameRoute", "Failed to decode IP and port")
    }
}

@Composable
fun AgrouAgrouNavHost(
    navController: NavHostController = rememberNavController(),
) {
    val gameViewModel = GameViewModel()

    val channel = remember {
        ManagedChannelBuilder.forAddress("10.0.2.2", 50051).usePlaintext().build()
    }

    val clientGameViewModel = remember { ClientGameViewModel(channel) }
    val gameState by clientGameViewModel.flowGameState.collectAsState()

    val clientPlayerViewModel = remember { ClientPlayerViewModel(channel, "username") }

    DisposableEffect(Unit) {
        onDispose {
            channel.shutdown()
        }
    }

    NavHost(
        navController = navController,
        startDestination = Destinations.HOME
    ) {
        composable(Destinations.HOME) {
            HomeRoute(
                onCreateGame = {
                    gameViewModel.createGame()
                    navController.navigate(Destinations.CREATE_GAME)
                },
                onJoinGame = { navController.navigate(Destinations.JOIN_GAME) }
            )
        }

        composable(Destinations.CREATE_GAME) {
            CreateGameRoute(
                gameViewModel,
                onStartGame = {
                    try {
                        gameViewModel.gameManager.value.startGame()
                        navController.navigate(Destinations.GAME_PHASE_GAMEMASTER)
                    } catch (e: IllegalStateException) {
                        Log.e("Navigation", "Could not start game: $e")
                    }
                }
            )
        }

        composable(Destinations.JOIN_GAME) {
            JoinGameRoute(
                onJoinGame = { username, gameCode ->
                    run {
                        clientPlayerViewModel.flowPlayerState.value.username = username
                        handleJoinGame(gameCode, navController)
                    }
                }
            )
        }

        composable(Destinations.CLIENT_GAME_PHASE) {
            clientGameViewModel.startStreaming();
            GameRunningRoute(gameState, clientPlayerViewModel)
        }

        composable(Destinations.GAME_PHASE_GAMEMASTER) {
            GamePhaseGameMasterRoute(gameViewModel)
        }
    }
}
