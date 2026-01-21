package edu.ucne.registroestudiantes.Presentation.Estudiantes.List

import edu.ucne.registroestudiantes.Domain.Model.Estudiante

data class EstudianteListUiState(
    val estudiantes: List<Estudiante> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)