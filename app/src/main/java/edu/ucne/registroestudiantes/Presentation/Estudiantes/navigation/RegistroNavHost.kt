package edu.ucne.registroestudiantes.Presentation.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.registroestudiantes.Presentation.estudiantes.asignatura.list.AsignaturaListScreen
import edu.ucne.registroestudiantes.Presentation.estudiantes.asignatura.edit.EditAsignaturaScreen
import edu.ucne.registroestudiantes.Presentation.estudiantes.edit.EditEstudianteScreen
import edu.ucne.registroestudiantes.Presentation.estudiantes.list.EstudianteListScreen
import kotlinx.coroutines.launch

@Composable
fun RegistroNavHost(
    navHostController: NavHostController
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    DrawerMenu(
        drawerState = drawerState,
        navHostController = navHostController
    ) {
        NavHost(
            navController = navHostController,
            startDestination = Screen.EstudianteList
        ) {
            // --- ESTUDIANTES ---
            composable<Screen.EstudianteList> {
                EstudianteListScreen(
                    onDrawer = { scope.launch { drawerState.open() } },
                    onNavigateToEdit = { id -> navHostController.navigate(Screen.Estudiante(id)) },
                    onNavigateToCreate = { navHostController.navigate(Screen.Estudiante(0)) }
                )
            }

            composable<Screen.Estudiante> {
                EditEstudianteScreen(
                    goBack = { navHostController.navigateUp() }
                )
            }

            // --- ASIGNATURAS ---
            composable<Screen.AsignaturaList> {
                AsignaturaListScreen(
                    onDrawer = { scope.launch { drawerState.open() } },
                    onEdit = { id -> navHostController.navigate(Screen.Asignatura(id)) },
                    onCreate = { navHostController.navigate(Screen.Asignatura(0)) }
                )
            }

            composable<Screen.Asignatura> {
                EditAsignaturaScreen(
                    onBack = { navHostController.navigateUp() },
                    onDrawer = { scope.launch { drawerState.open() } }
                )
            }
        }
    }
}