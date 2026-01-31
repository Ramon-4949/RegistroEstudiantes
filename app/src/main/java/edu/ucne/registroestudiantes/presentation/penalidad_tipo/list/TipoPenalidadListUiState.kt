package edu.ucne.registroestudiantes.presentation.penalidad_tipo.list

import edu.ucne.registroestudiantes.domain.penalidad_tipo.model.TipoPenalidad

data class TipoPenalidadListUiState(
    val tiposPenalidades: List<TipoPenalidad> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)