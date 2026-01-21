package edu.ucne.registroestudiantes.Data.Mapper

import edu.ucne.registroestudiantes.Data.Local.EstudianteEntity
import edu.ucne.registroestudiantes.Domain.Model.Estudiante

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