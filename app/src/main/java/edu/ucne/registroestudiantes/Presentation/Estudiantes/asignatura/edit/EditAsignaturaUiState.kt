package edu.ucne.registroestudiantes.Presentation.estudiantes.asignatura.edit

data class EditAsignaturaUiState(
    val asignaturaId: Int? = null,
    val codigo: String = "",
    val codigoError: String? = null,
    val nombre: String = "",
    val nombreError: String? = null,
    val aula: String = "",
    val aulaError: String? = null,
    val creditos: String = "",
    val creditosError: String? = null,
    val saved: Boolean = false
)