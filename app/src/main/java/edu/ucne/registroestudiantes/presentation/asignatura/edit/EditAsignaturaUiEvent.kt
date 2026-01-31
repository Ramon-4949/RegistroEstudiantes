package edu.ucne.registroestudiantes.presentation.asignatura.edit

sealed interface EditAsignaturaUiEvent {
    data class CodigoChanged(val codigo: String) : EditAsignaturaUiEvent
    data class NombreChanged(val nombre: String) : EditAsignaturaUiEvent
    data class AulaChanged(val aula: String) : EditAsignaturaUiEvent
    data class CreditosChanged(val creditos: String) : EditAsignaturaUiEvent
    data object Save : EditAsignaturaUiEvent
    data object Nuevo : EditAsignaturaUiEvent
}