package edu.ucne.registroestudiantes.data.mapper

import edu.ucne.registroestudiantes.data.local.entities.AsignaturaEntity
import edu.ucne.registroestudiantes.domain.asignatura.model.Asignatura

fun AsignaturaEntity.toDomain(): Asignatura {
    return Asignatura(
        asignaturaId = asignaturaId ?: 0,
        codigo = codigo,
        nombre = nombre,
        aula = aula,
        creditos = creditos
    )
}

fun Asignatura.toEntity(): AsignaturaEntity {
    return AsignaturaEntity(
        asignaturaId = if (asignaturaId == 0) null else asignaturaId,
        codigo = codigo,
        nombre = nombre,
        aula = aula,
        creditos = creditos
    )
}