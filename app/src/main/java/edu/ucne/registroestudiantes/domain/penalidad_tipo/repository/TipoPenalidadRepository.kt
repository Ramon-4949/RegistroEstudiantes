package edu.ucne.registroestudiantes.domain.penalidad_tipo.repository

import edu.ucne.registroestudiantes.domain.penalidad_tipo.model.TipoPenalidad
import kotlinx.coroutines.flow.Flow

interface TipoPenalidadRepository {
    fun observeTiposPenalidades(): Flow<List<TipoPenalidad>>
    suspend fun getTipoPenalidad(id: Int): TipoPenalidad?
    suspend fun upsert(tipoPenalidad: TipoPenalidad)
    suspend fun delete(id: Int)
    suspend fun findByNombre(nombre: String): TipoPenalidad?
}