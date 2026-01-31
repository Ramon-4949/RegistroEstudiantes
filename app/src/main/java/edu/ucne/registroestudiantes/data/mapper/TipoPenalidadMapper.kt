package edu.ucne.registroestudiantes.data.mapper

import edu.ucne.registroestudiantes.data.local.entities.TipoPenalidadEntity
import edu.ucne.registroestudiantes.domain.penalidad_tipo.model.TipoPenalidad

fun TipoPenalidadEntity.toDomain() = TipoPenalidad(
    tipoId = tipoId ?: 0,
    nombre = nombre,
    descripcion = descripcion,
    puntosDescuento = puntosDescuento
)

fun TipoPenalidad.toEntity() = TipoPenalidadEntity(
    tipoId = if (tipoId == 0) null else tipoId,
    nombre = nombre,
    descripcion = descripcion,
    puntosDescuento = puntosDescuento
)