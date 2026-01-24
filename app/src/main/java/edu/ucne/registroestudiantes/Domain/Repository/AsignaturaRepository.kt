package edu.ucne.registroestudiantes.Domain.Repository

import edu.ucne.registroestudiantes.Domain.Model.Asignatura
import kotlinx.coroutines.flow.Flow

interface AsignaturaRepository {
    suspend fun save(asignatura: Asignatura)
    suspend fun delete(asignatura: Asignatura)
    suspend fun find(id: Int): Asignatura?
    suspend fun findByNombre(nombre: String): Asignatura?
    fun getAll(): Flow<List<Asignatura>>
}