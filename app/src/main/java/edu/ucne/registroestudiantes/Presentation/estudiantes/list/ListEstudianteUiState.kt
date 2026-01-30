package edu.ucne.registroestudiantes.Presentation.estudiantes.list

import edu.ucne.registroestudiantes.domain.model.Estudiante

data class EstudianteListUiState(
    val estudiantes: List<Estudiante> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)