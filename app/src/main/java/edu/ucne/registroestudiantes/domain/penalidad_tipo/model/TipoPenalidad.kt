package edu.ucne.registroestudiantes.domain.penalidad_tipo.model

data class TipoPenalidad(
    val tipoId: Int = 0,
    val nombre: String,
    val descripcion: String,
    val puntosDescuento: Int
)