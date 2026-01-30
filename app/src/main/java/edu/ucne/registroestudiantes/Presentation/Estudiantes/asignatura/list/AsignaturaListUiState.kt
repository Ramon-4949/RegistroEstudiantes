package edu.ucne.registroestudiantes.Presentation.estudiantes.asignatura.list

import edu.ucne.registroestudiantes.domain.model.Asignatura

data class AsignaturaListUiState(
    val asignaturas: List<Asignatura> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)