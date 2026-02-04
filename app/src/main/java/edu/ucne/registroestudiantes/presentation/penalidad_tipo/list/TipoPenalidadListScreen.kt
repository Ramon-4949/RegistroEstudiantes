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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registroestudiantes.domain.penalidad_tipo.model.TipoPenalidad

@Composable
fun TipoPenalidadListScreen(
    onDrawer: () -> Unit,
    onCreate: () -> Unit,
    onEdit: (Int) -> Unit,
    viewModel: TipoPenalidadListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TipoPenalidadListBody(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onDrawer = onDrawer,
        onCreate = onCreate,
        onEdit = onEdit
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipoPenalidadListBody(
    uiState: TipoPenalidadListUiState,
    onEvent: (TipoPenalidadListUiEvent) -> Unit,
    onDrawer: () -> Unit,
    onCreate: () -> Unit,
    onEdit: (Int) -> Unit
) {
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
            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (uiState.tiposPenalidades.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Sin datos",
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("No hay tipos de penalidades.")
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.tiposPenalidades) { tipo ->
                        TipoPenalidadRow(
                            tipo = tipo,
                            onEdit = { onEdit(tipo.tipoId) },
                            onSelectDelete = { onEvent(TipoPenalidadListUiEvent.OnSelectTipoToDelete(tipo)) }
                        )
                    }
                }
            }
        }

        if (uiState.showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { onEvent(TipoPenalidadListUiEvent.OnDismissDialog) },
                title = { Text("Eliminar Tipo") },
                text = { Text("¿Eliminar ${uiState.tipoToDelete?.nombre}?") },
                confirmButton = {
                    TextButton(
                        onClick = { onEvent(TipoPenalidadListUiEvent.OnConfirmDelete) }
                    ) { Text("Eliminar", color = MaterialTheme.colorScheme.error) }
                },
                dismissButton = {
                    TextButton(onClick = { onEvent(TipoPenalidadListUiEvent.OnDismissDialog) }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@Composable
fun TipoPenalidadRow(
    tipo: TipoPenalidad,
    onEdit: () -> Unit,
    onSelectDelete: () -> Unit
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
            IconButton(onClick = onSelectDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}