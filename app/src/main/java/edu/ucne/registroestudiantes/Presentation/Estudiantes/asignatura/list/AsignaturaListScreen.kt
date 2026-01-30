package edu.ucne.registroestudiantes.Presentation.estudiantes.asignatura.list

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registroestudiantes.domain.model.Asignatura
import edu.ucne.registroestudiantes.ui.theme.RegistroEstudiantesTheme

@Composable
fun AsignaturaListScreen(
    onDrawer: () -> Unit,
    onCreate: () -> Unit,
    onEdit: (Int) -> Unit,
    viewModel: AsignaturaListViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    AsignaturaListBody(
        asignaturas = state.asignaturas,
        onDrawer = onDrawer,
        onCreate = onCreate,
        onEdit = onEdit,
        onDelete = { asignatura ->
            viewModel.onEvent(AsignaturaListUiEvent.Delete(asignatura.asignaturaId))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsignaturaListBody(
    asignaturas: List<Asignatura>,
    onDrawer: () -> Unit,
    onCreate: () -> Unit,
    onEdit: (Int) -> Unit,
    onDelete: (Asignatura) -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var asignaturaToDelete by remember { mutableStateOf<Asignatura?>(null) }

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
            FloatingActionButton(
                onClick = onCreate,
                modifier = Modifier.testTag("fab_add")
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Crear Asignatura"
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (asignaturas.isEmpty()) {
                Log.d("AsignaturaListScreen", "No hay asignaturas registradas")
                Text(
                    text = "No hay asignaturas registradas",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.titleMedium
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .testTag("task_list")
                ) {
                    items(asignaturas) { asignatura ->
                        AsignaturaRow(
                            asignatura = asignatura,
                            onEdit = { onEdit(asignatura.asignaturaId) },
                            onDelete = {
                                asignaturaToDelete = asignatura
                                showDeleteDialog = true
                            },
                            onClick = { onEdit(asignatura.asignaturaId) }
                        )
                    }
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
                            asignaturaToDelete?.let { onDelete(it) }
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
    asignatura: Asignatura,
    onEdit: (Int) -> Unit,
    onDelete: (Int) -> Unit,
    onClick: (Asignatura) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .testTag("task_card_${asignatura.asignaturaId}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onClick(asignatura) }
            ) {
                Text(
                    text = "${asignatura.codigo} - ${asignatura.nombre}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Aula: ${asignatura.aula} | Créditos: ${asignatura.creditos}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            IconButton(
                onClick = { onEdit(asignatura.asignaturaId) },
                modifier = Modifier
                    .size(40.dp)
                    .testTag("edit_button_${asignatura.asignaturaId}")
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            IconButton(
                onClick = { onDelete(asignatura.asignaturaId) },
                modifier = Modifier
                    .size(40.dp)
                    .testTag("delete_button_${asignatura.asignaturaId}")
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AsignaturaListBodyPreview() {
    RegistroEstudiantesTheme {
        AsignaturaListBody(
            asignaturas = listOf(
                Asignatura(1, "MAT", "Matemáticas", "A-1", 4),
                Asignatura(2, "ESP", "Español", "B-2", 3)
            ),
            onDrawer = {},
            onCreate = {},
            onEdit = {},
            onDelete = {}
        )
    }
}