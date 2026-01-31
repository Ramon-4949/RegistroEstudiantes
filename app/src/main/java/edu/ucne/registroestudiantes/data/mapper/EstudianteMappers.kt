package edu.ucne.registroestudiantes.data.mapper

import edu.ucne.registroestudiantes.data.local.entities.EstudianteEntity
import edu.ucne.registroestudiantes.domain.estudiantes.model.Estudiante

fun EstudianteEntity.toDomain() = Estudiante(
    estudianteId = estudianteId,
    nombres = nombres,
    email = email,
    edad = edad
)
fun Estudiante.toEntity() = EstudianteEntity(
    estudianteId = estudianteId,
    nombres = nombres,
    email = email,
    edad = edad
)