package edu.ucne.registroestudiantes.Data.Mapper

import edu.ucne.registroestudiantes.Data.Local.entities.AsignaturaEntity
import edu.ucne.registroestudiantes.Domain.Model.Asignatura

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