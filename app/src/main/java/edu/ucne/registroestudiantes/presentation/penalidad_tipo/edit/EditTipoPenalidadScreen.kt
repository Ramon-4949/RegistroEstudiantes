package edu.ucne.registroestudiantes.presentation.penalidad_tipo.edit

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registroestudiantes.presentation.penalidad_tipo.edit.EditTipoPenalidadUiEvent
import edu.ucne.registroestudiantes.presentation.penalidad_tipo.edit.EditTipoPenalidadUiState
import edu.ucne.registroestudiantes.presentation.penalidad_tipo.edit.EditTipoPenalidadViewModel
import edu.ucne.registroestudiantes.ui.theme.RegistroEstudiantesTheme

@Composable
fun EditTipoPenalidadScreen(
    viewModel: EditTipoPenalidadViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onDrawer: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.saved) {
        if (uiState.saved) {
            onBack()
            viewModel.onEvent(EditTipoPenalidadUiEvent.Nuevo)
        }
    }

    EditTipoPenalidadBody(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onDrawer = onDrawer
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTipoPenalidadBody(
    uiState: EditTipoPenalidadUiState,
    onEvent: (EditTipoPenalidadUiEvent) -> Unit,
    onDrawer: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Registro Tipo Penalidad") },
                navigationIcon = {
                    IconButton(onClick = onDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent(EditTipoPenalidadUiEvent.Save) }) {
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
                value = uiState.nombre,
                onValueChange = { onEvent(EditTipoPenalidadUiEvent.NombreChanged(it)) },
                label = { Text("Nombre") },
                isError = uiState.nombreError != null,
                supportingText = { uiState.nombreError?.let { Text(it, color = MaterialTheme.colorScheme.error) } }
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.descripcion,
                onValueChange = { onEvent(EditTipoPenalidadUiEvent.DescripcionChanged(it)) },
                label = { Text("Descripción") },
                isError = uiState.descripcionError != null,
                supportingText = { uiState.descripcionError?.let { Text(it, color = MaterialTheme.colorScheme.error) } }
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.puntosDescuento,
                onValueChange = { onEvent(EditTipoPenalidadUiEvent.PuntosChanged(it)) },
                label = { Text("Puntos Descuento") },
                isError = uiState.puntosError != null,
                supportingText = { uiState.puntosError?.let { Text(it, color = MaterialTheme.colorScheme.error) } }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditTipoPenalidadPreview() {
    RegistroEstudiantesTheme {
        EditTipoPenalidadBody(
            uiState = EditTipoPenalidadUiState(),
            onEvent = {},
            onDrawer = {}
        )
    }
}