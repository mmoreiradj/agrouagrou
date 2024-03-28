package fr.agrouagrou.mobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.agrouagrou.mobileapp.ui.theme.AppTheme

class MainViewModel : ViewModel()

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(topBar = {
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
                    }, bottomBar = {
                        NavigationBar(navController = navController)
                    }) { innerPadding ->
                        Column(
                            modifier = Modifier.padding(innerPadding),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            NavHost(navController = navController, startDestination = "home") {
                                composable("home") {
                                    Text(text = "nain de maison")
                                }
                                composable("settings") {
                                    Text(text = "nain de jardin")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

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