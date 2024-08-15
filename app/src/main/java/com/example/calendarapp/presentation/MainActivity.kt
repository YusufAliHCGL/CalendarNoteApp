package com.example.calendarapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.calendarapp.presentation.note_details.DetailScreen
import com.example.calendarapp.presentation.notes.HomeScreen
import com.example.calendarapp.presentation.util.Constants.NOTE_ID
import com.example.calendarapp.presentation.util.Screen
import com.example.calendarapp.presentation.ui.theme.CalendarAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalendarAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    println(innerPadding)
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.HomeScreen.route
                    ) {
                        composable(route = Screen.HomeScreen.route) {
                            HomeScreen(
                                navController = navController
                            )
                        }

                        composable(
                            route = Screen.DetailScreen.route+"/{${NOTE_ID}}",
                            arguments = listOf(
                                navArgument(
                                    name = NOTE_ID
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            DetailScreen(navController = navController)
                        }

                    }
                }
            }
        }
    }
}
