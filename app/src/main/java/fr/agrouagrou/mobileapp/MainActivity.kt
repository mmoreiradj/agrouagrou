package fr.agrouagrou.mobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.rememberNavController
import fr.agrouagrou.mobileapp.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                AgrouAgrouNavHost()
            }
        }
    }
}

/**
@Composable
fun NavigationBar(navController: NavController) {
NavigationBar {
NavigationBarItem(label = { Text(text = "Home") },
selected = false,
onClick = { navController.navigate("home") },
icon = {
Icon(
imageVector = Icons.Filled.Home, contentDescription = "home button"
)
})
NavigationBarItem(label = { Text(text = "Settings") },
selected = false,
onClick = { navController.navigate("settings") },
icon = {
Icon(
imageVector = Icons.Filled.Settings, contentDescription = "settings button"
)
})
}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
TopAppBar(
title = {
Column(
modifier = Modifier.fillMaxSize(),
verticalArrangement = Arrangement.Center,
horizontalAlignment = Alignment.CenterHorizontally
) {
Text(
text = "\uD83D\uDC3A AgrouAgrou",
)
}
}, colors = topAppBarColors(
titleContentColor = MaterialTheme.colorScheme.primary,
containerColor = MaterialTheme.colorScheme.primaryContainer,
)
)
}
 **/