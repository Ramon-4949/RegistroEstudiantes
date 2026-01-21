package edu.ucne.registroestudiantes.Domain.Repository

import edu.ucne.registroestudiantes.Domain.Model.Estudiante
import kotlinx.coroutines.flow.Flow

interface EstudianteRepository {

    fun getAll(): Flow<List<Estudiante>>

    suspend fun find(id: Int): Estudiante?

    suspend fun save(estudiante: Estudiante)
    suspend fun delete(estudiante: Estudiante)

    suspend fun getByNombre(nombre: String): Estudiante?
}