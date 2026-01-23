package edu.ucne.registroestudiantes.Presentation.Estudiantes.Asignatura

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registroestudiantes.Data.Local.entities.AsignaturaEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsignaturaListScreen(
    viewModel: AsignaturaListViewModel = hiltViewModel(),
    onDrawer: () -> Unit, // Para abrir el menú lateral
    onCreate: () -> Unit,
    onEdit: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var asignaturaToDelete by remember { mutableStateOf<AsignaturaEntity?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Lista de Asignaturas") },
                navigationIcon = {
                    IconButton(onClick = onDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onCreate) {
                Icon(Icons.Default.Add, contentDescription = "Crear Asignatura")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.asignaturas) { asignatura ->
                    AsignaturaRow(
                        asignatura = asignatura,
                        onEdit = { asignatura.asignaturaId?.let { onEdit(it) } },
                        onDelete = {
                            asignaturaToDelete = asignatura
                            showDeleteDialog = true
                        }
                    )
                }
            }
        }

        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Eliminar Asignatura") },
                text = { Text("¿Deseas eliminar la asignatura ${asignaturaToDelete?.nombre}?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            asignaturaToDelete?.let { viewModel.delete(it) }
                            showDeleteDialog = false
                        }
                    ) { Text("Eliminar", color = MaterialTheme.colorScheme.error) }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) { Text("Cancelar") }
                }
            )
        }
    }
}

@Composable
fun AsignaturaRow(
    asignatura: AsignaturaEntity,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${asignatura.codigo} - ${asignatura.nombre}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Aula: ${asignatura.aula} | Créditos: ${asignatura.creditos}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar", tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}