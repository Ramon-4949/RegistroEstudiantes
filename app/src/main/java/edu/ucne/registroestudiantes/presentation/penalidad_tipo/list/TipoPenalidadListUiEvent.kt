package edu.ucne.registroestudiantes.presentation.penalidad_tipo.list

import edu.ucne.registroestudiantes.domain.penalidad_tipo.model.TipoPenalidad

sealed interface TipoPenalidadListUiEvent {
    data object Load : TipoPenalidadListUiEvent
    data class OnSelectTipoToDelete(val tipo: TipoPenalidad) : TipoPenalidadListUiEvent
    data object OnConfirmDelete : TipoPenalidadListUiEvent
    data object OnDismissDialog : TipoPenalidadListUiEvent
}