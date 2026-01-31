package edu.ucne.registroestudiantes.presentation.penalidad_tipo.list

sealed interface TipoPenalidadListUiEvent {
    data object Load : TipoPenalidadListUiEvent
    data class Delete(val tipoId: Int) : TipoPenalidadListUiEvent
}