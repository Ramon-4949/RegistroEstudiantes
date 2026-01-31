package edu.ucne.registroestudiantes.presentation.estudiantes.edit

data class EditEstudianteUiState(
    val estudianteId: Int? = null,
    val nombre: String = "",
    val nombreError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val edad: String = "",
    val edadError: String? = null,
    val isSaving: Boolean = false,
    val saved: Boolean = false
)