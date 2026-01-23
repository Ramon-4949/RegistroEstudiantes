package edu.ucne.registroestudiantes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.registroestudiantes.Presentation.Estudiantes.Edit.EditEstudianteScreen
import edu.ucne.registroestudiantes.Presentation.Estudiantes.List.EstudianteListScreen
import edu.ucne.registroestudiantes.Presentation.Navigation.RegistroEstudiantesDrawer
import edu.ucne.registroestudiantes.Presentation.Navigation.Screen
import edu.ucne.registroestudiantes.ui.theme.RegistroEstudiantesTheme
import kotlinx.coroutines.launch
import edu.ucne.registroestudiantes.Presentation.Estudiantes.Asignatura.AsignaturaListScreen
import edu.ucne.registroestudiantes.Presentation.Estudiantes.Asignatura.AsignaturaScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegistroEstudiantesTheme {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                RegistroEstudiantesDrawer(
                    drawerState = drawerState,
                    onNavigate = { route ->
                        navController.navigate(route)
                        scope.launch { drawerState.close() }
                    }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.AsignaturaList
                    ) {
                        composable<Screen.EstudianteList> {
                            EstudianteListScreen(
                                onNavigateToEdit = { id -> navController.navigate(Screen.Estudiante(id)) },
                                onNavigateToCreate = { navController.navigate(Screen.Estudiante(0)) }
                            )
                        }
                        composable<Screen.Estudiante> { backStackEntry ->
                            EditEstudianteScreen(goBack = { navController.navigateUp() })
                        }
                        composable<Screen.AsignaturaList> {
                            AsignaturaListScreen(
                                onDrawer = { scope.launch { drawerState.open() } },
                                onCreate = { navController.navigate(Screen.Asignatura(0)) },
                                onEdit = { id -> navController.navigate(Screen.Asignatura(id)) }
                            )
                        }
                        composable<Screen.Asignatura> { backStackEntry ->
                            val args = backStackEntry.toRoute<Screen.Asignatura>()
                            AsignaturaScreen(
                                asignaturaId = args.asignaturaId,
                                onBack = { navController.navigateUp() }
                            )
                        }
                    }
                }
            }
        }
    }
}