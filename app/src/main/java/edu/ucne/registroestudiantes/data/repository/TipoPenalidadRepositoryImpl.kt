package edu.ucne.registroestudiantes.data.repository

import edu.ucne.registroestudiantes.data.local.dao.TipoPenalidadDao
import edu.ucne.registroestudiantes.data.mapper.toDomain
import edu.ucne.registroestudiantes.data.mapper.toEntity
import edu.ucne.registroestudiantes.domain.penalidad_tipo.model.TipoPenalidad
import edu.ucne.registroestudiantes.domain.penalidad_tipo.repository.TipoPenalidadRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TipoPenalidadRepositoryImpl @Inject constructor(
    private val dao: TipoPenalidadDao
) : TipoPenalidadRepository {

    override fun observeTiposPenalidades(): Flow<List<TipoPenalidad>> {
        return dao.getAll().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun getTipoPenalidad(id: Int): TipoPenalidad? {
        return dao.find(id)?.toDomain()
    }

    override suspend fun upsert(tipoPenalidad: TipoPenalidad) {
        dao.upsert(tipoPenalidad.toEntity())
    }

    override suspend fun delete(id: Int) {
        val entity = dao.find(id)
        entity?.let { dao.delete(it) }
    }

    override suspend fun findByNombre(nombre: String): TipoPenalidad? {
        return dao.findByNombre(nombre)?.toDomain()
    }
}