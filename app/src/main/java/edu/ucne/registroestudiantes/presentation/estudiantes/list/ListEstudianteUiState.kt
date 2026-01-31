package edu.ucne.registroestudiantes.presentation.estudiantes.list

import edu.ucne.registroestudiantes.domain.estudiantes.model.Estudiante

data class EstudianteListUiState(
    val estudiantes: List<Estudiante> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)