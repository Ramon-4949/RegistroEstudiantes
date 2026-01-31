package edu.ucne.registroestudiantes.presentation.estudiantes.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EditEstudianteScreen(
    viewModel: EditEstudianteViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.saved) {
        goBack()
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text("Registro de Estudiantes", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(16.dp))

            // Nombre
            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = { viewModel.onEvent(EditEstudianteUiEvent.NombreChanged(it)) },
                label = { Text("Nombres") },
                isError = uiState.nombreError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (uiState.nombreError != null) {
                Text(text = uiState.nombreError ?: "", color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Email
            OutlinedTextField(
                value = uiState.email,
                onValueChange = { viewModel.onEvent(EditEstudianteUiEvent.EmailChanged(it)) },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = uiState.emailError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (uiState.emailError != null) {
                Text(text = uiState.emailError ?: "", color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Edad
            OutlinedTextField(
                value = uiState.edad,
                onValueChange = { viewModel.onEvent(EditEstudianteUiEvent.EdadChanged(it)) },
                label = { Text("Edad") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = uiState.edadError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (uiState.edadError != null) {
                Text(text = uiState.edadError ?: "", color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.onEvent(EditEstudianteUiEvent.Save) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar")
            }
        }
    }
}