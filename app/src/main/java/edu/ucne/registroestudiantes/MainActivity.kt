package edu.ucne.registroestudiantes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.registroestudiantes.Presentation.Estudiantes.Edit.EditEstudianteScreen
import edu.ucne.registroestudiantes.Presentation.Estudiantes.List.EstudianteListScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "estudianteList"
                    ) {
                        // 1. PANTALLA DE LISTA
                        composable("estudianteList") {
                            EstudianteListScreen(
                                onNavigateToEdit = { id ->
                                    navController.navigate("editEstudiante/$id")
                                },
                                onNavigateToCreate = {
                                    navController.navigate("editEstudiante/0")
                                }
                            )
                        }


                        composable(
                            route = "editEstudiante/{estudianteId}",
                            arguments = listOf(
                                navArgument("estudianteId") { type = NavType.IntType }
                            )
                        ) { backStackEntry ->
                            val id = backStackEntry.arguments?.getInt("estudianteId") ?: 0

                            EditEstudianteScreen(
                                goBack = { navController.navigateUp() }
                            )
                        }
                    }
                }
            }
        }
    }
}