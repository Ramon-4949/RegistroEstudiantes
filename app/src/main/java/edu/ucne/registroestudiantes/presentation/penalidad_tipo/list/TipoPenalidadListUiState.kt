package edu.ucne.registroestudiantes.presentation.penalidad_tipo.list

import edu.ucne.registroestudiantes.domain.penalidad_tipo.model.TipoPenalidad

data class TipoPenalidadListUiState(
    val isLoading: Boolean = false,
    val tiposPenalidades: List<TipoPenalidad> = emptyList(),
    val errorMessage: String? = null,
    val tipoToDelete: TipoPenalidad? = null,
    val showDeleteDialog: Boolean = false
)