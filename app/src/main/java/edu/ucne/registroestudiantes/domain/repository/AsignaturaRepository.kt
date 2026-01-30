package edu.ucne.registroestudiantes.domain.repository

import edu.ucne.registroestudiantes.domain.model.Asignatura
import kotlinx.coroutines.flow.Flow

interface AsignaturaRepository {
    fun observeAsignaturas(): Flow<List<Asignatura>>
    suspend fun getAsignatura(id: Int): Asignatura?
    suspend fun upsert(asignatura: Asignatura)
    suspend fun delete(id: Int)
    suspend fun findByNombre(nombre: String): Asignatura?
}