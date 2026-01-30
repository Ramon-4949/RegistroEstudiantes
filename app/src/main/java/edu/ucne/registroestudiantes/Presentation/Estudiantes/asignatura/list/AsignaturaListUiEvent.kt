package edu.ucne.registroestudiantes.Presentation.estudiantes.asignatura.list

sealed interface AsignaturaListUiEvent {
    data object Load : AsignaturaListUiEvent
    data class Delete(val asignaturaId: Int) : AsignaturaListUiEvent
}