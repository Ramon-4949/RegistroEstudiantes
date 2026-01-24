package edu.ucne.registroestudiantes.Domain.Repository

import edu.ucne.registroestudiantes.Data.Local.entities.AsignaturaEntity
import kotlinx.coroutines.flow.Flow

interface AsignaturaRepository {
    suspend fun save(asignatura: AsignaturaEntity)
    suspend fun delete(asignatura: AsignaturaEntity)
    suspend fun find(id: Int): AsignaturaEntity?
    suspend fun findByNombre(nombre: String): AsignaturaEntity?
    fun getAll(): Flow<List<AsignaturaEntity>>
}