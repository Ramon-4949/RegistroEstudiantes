package edu.ucne.registroestudiantes.presentation.asignatura.list

import edu.ucne.registroestudiantes.domain.asignatura.model.Asignatura

data class AsignaturaListUiState(
    val asignaturas: List<Asignatura> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)