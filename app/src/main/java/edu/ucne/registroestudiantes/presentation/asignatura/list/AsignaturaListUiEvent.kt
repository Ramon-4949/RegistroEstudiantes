package edu.ucne.registroestudiantes.presentation.asignatura.list

sealed interface AsignaturaListUiEvent {
    data object Load : AsignaturaListUiEvent
    data class Delete(val asignaturaId: Int) : AsignaturaListUiEvent
}