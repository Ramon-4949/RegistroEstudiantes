package edu.ucne.registroestudiantes.presentation.penalidad_tipo.edit

sealed interface EditTipoPenalidadUiEvent {
    data class NombreChanged(val nombre: String) : EditTipoPenalidadUiEvent
    data class DescripcionChanged(val descripcion: String) : EditTipoPenalidadUiEvent
    data class PuntosChanged(val puntos: String) : EditTipoPenalidadUiEvent
    data object Save : EditTipoPenalidadUiEvent
    data object Nuevo : EditTipoPenalidadUiEvent
}