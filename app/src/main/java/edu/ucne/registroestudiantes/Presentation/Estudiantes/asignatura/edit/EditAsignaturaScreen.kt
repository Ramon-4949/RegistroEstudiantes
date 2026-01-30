package edu.ucne.registroestudiantes.Presentation.estudiantes.asignatura.edit

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registroestudiantes.ui.theme.RegistroEstudiantesTheme

@Composable
fun EditAsignaturaScreen(
    viewModel: EditAsignaturaViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onDrawer: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.saved) {
        if (uiState.saved) {
            onBack()
            viewModel.onEvent(EditAsignaturaUiEvent.Nuevo)
        }
    }

    EditAsignaturaBody(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onBack = onBack,
        onDrawer = onDrawer
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAsignaturaBody(
    uiState: EditAsignaturaUiState,
    onEvent: (EditAsignaturaUiEvent) -> Unit,
    onBack: () -> Unit,
    onDrawer: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Registro Asignatura") },
                navigationIcon = {
                    IconButton(onClick = onDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent(EditAsignaturaUiEvent.Save) }) {
                Icon(Icons.Default.Check, contentDescription = "Guardar")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.codigo,
                onValueChange = { onEvent(EditAsignaturaUiEvent.CodigoChanged(it)) },
                label = { Text("Código") },
                isError = uiState.codigoError != null,
                supportingText = {
                    if (uiState.codigoError != null) {
                        Text(text = uiState.codigoError, color = MaterialTheme.colorScheme.error)
                    }
                }
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.nombre,
                onValueChange = { onEvent(EditAsignaturaUiEvent.NombreChanged(it)) },
                label = { Text("Nombre") },
                isError = uiState.nombreError != null,
                supportingText = {
                    if (uiState.nombreError != null) {
                        Text(text = uiState.nombreError, color = MaterialTheme.colorScheme.error)
                    }
                }
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.aula,
                onValueChange = { onEvent(EditAsignaturaUiEvent.AulaChanged(it)) },
                label = { Text("Aula") },
                isError = uiState.aulaError != null,
                supportingText = {
                    if (uiState.aulaError != null) {
                        Text(text = uiState.aulaError, color = MaterialTheme.colorScheme.error)
                    }
                }
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.creditos,
                onValueChange = { onEvent(EditAsignaturaUiEvent.CreditosChanged(it)) },
                label = { Text("Créditos") },
                isError = uiState.creditosError != null,
                supportingText = {
                    if (uiState.creditosError != null) {
                        Text(text = uiState.creditosError, color = MaterialTheme.colorScheme.error)
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditAsignaturaPreview() {
    RegistroEstudiantesTheme {
        EditAsignaturaBody(
            uiState = EditAsignaturaUiState(
                codigo = "MAT-01",
                nombre = "Matemáticas",
                aula = "A-2",
                creditos = "4"
            ),
            onEvent = {},
            onBack = {},
            onDrawer = {}
        )
    }
}