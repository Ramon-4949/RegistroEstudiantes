package edu.ucne.registroestudiantes.Presentation.Estudiantes.Asignatura

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsignaturaScreen(
    viewModel: AsignaturaViewModel = hiltViewModel(),
    asignaturaId: Int,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(asignaturaId) {
        viewModel.getAsignatura(asignaturaId)
    }

    LaunchedEffect(uiState.saved) {
        if (uiState.saved) {
            onBack()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(if (asignaturaId > 0) "Editar Asignatura" else "Nueva Asignatura") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.save() }) {
                Icon(Icons.Default.Check, contentDescription = "Guardar")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // CODIGO
            OutlinedTextField(
                value = uiState.codigo,
                onValueChange = viewModel::onCodigoChange,
                label = { Text("Código") },
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.codigoError != null,
                supportingText = { uiState.codigoError?.let { Text(it) } }
            )

            // NOMBRE
            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = viewModel::onNombreChange,
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.nombreError != null,
                supportingText = { uiState.nombreError?.let { Text(it) } }
            )

            // AULA
            OutlinedTextField(
                value = uiState.aula,
                onValueChange = viewModel::onAulaChange,
                label = { Text("Aula") },
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.aulaError != null,
                supportingText = { uiState.aulaError?.let { Text(it) } }
            )

            // CREDITOS
            OutlinedTextField(
                value = uiState.creditos,
                onValueChange = viewModel::onCreditosChange,
                label = { Text("Créditos") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.creditosError != null,
                supportingText = { uiState.creditosError?.let { Text(it) } }
            )
        }
    }
}