package edu.ucne.registroestudiantes.presentation.penalidad_tipo.edit

data class EditTipoPenalidadUiState(
    val tipoId: Int? = null,
    val nombre: String = "",
    val nombreError: String? = null,
    val descripcion: String = "",
    val descripcionError: String? = null,
    val puntosDescuento: String = "",
    val puntosError: String? = null,
    val saved: Boolean = false
)