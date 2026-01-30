package edu.ucne.registroestudiantes.Presentation.estudiantes.edit

sealed interface EditEstudianteUiEvent {
    data class NombreChanged(val nombre: String) : EditEstudianteUiEvent
    data class EmailChanged(val email: String) : EditEstudianteUiEvent
    data class EdadChanged(val edad: String) : EditEstudianteUiEvent
    data object Save : EditEstudianteUiEvent
    data object New : EditEstudianteUiEvent
}