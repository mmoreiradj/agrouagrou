package fr.agrouagrou.mobileapp

import androidx.annotation.StringRes
import fr.agrouagrou.grpc_server.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Home : Screen("home", R.string.home)
    object Settings : Screen("settings", R.string.settings)
}
