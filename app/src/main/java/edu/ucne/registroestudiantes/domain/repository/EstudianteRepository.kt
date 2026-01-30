package edu.ucne.registroestudiantes.domain.repository

import edu.ucne.registroestudiantes.domain.model.Estudiante
import kotlinx.coroutines.flow.Flow

interface EstudianteRepository {

    fun getAll(): Flow<List<Estudiante>>

    suspend fun find(id: Int): Estudiante?

    suspend fun save(estudiante: Estudiante)
    suspend fun delete(estudiante: Estudiante)

    suspend fun getByNombre(nombre: String): Estudiante?
}