package edu.ucne.registroestudiantes.presentation.penalidad_tipo.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registroestudiantes.domain.penalidad_tipo.model.TipoPenalidad
import edu.ucne.registroestudiantes.ui.theme.RegistroEstudiantesTheme

@Composable
fun TipoPenalidadListScreen(
    onDrawer: () -> Unit,
    onCreate: () -> Unit,
    onEdit: (Int) -> Unit,
    viewModel: TipoPenalidadListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TipoPenalidadListBody(
        tiposPenalidades = uiState.tiposPenalidades,
        onDrawer = onDrawer,
        onCreate = onCreate,
        onEdit = onEdit,
        onDelete = { tipo ->
            viewModel.onEvent(TipoPenalidadListUiEvent.Delete(tipo.tipoId))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipoPenalidadListBody(
    tiposPenalidades: List<TipoPenalidad>,
    onDrawer: () -> Unit,
    onCreate: () -> Unit,
    onEdit: (Int) -> Unit,
    onDelete: (TipoPenalidad) -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var tipoToDelete by remember { mutableStateOf<TipoPenalidad?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Tipos de Penalidades") },
                navigationIcon = {
                    IconButton(onClick = onDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onCreate) {
                Icon(Icons.Default.Add, contentDescription = "Crear Tipo")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (tiposPenalidades.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Sin datos",
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No hay tipos de penalidades.",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(tiposPenalidades) { tipo ->
                        TipoPenalidadRow(
                            tipo = tipo,
                            onEdit = { onEdit(tipo.tipoId) },
                            onDelete = {
                                tipoToDelete = tipo
                                showDeleteDialog = true
                            }
                        )
                    }
                }
            }
        }

        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Eliminar Tipo") },
                text = { Text("¿Eliminar ${tipoToDelete?.nombre}?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            tipoToDelete?.let { onDelete(it) }
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
fun TipoPenalidadRow(
    tipo: TipoPenalidad,
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = tipo.nombre, style = MaterialTheme.typography.titleMedium)
                Text(text = "Descuento: ${tipo.puntosDescuento}", style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Editar", tint = MaterialTheme.colorScheme.primary)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TipoPenalidadListPreview() {
    RegistroEstudiantesTheme {
        TipoPenalidadListBody(
            tiposPenalidades = listOf(
                TipoPenalidad(1, "Llegada Tardía", "5 min tarde", 10),
                TipoPenalidad(2, "Sin Uniforme", "Sin polo", 15)
            ),
            onDrawer = {}, onCreate = {}, onEdit = {}, onDelete = {}
        )
    }
}