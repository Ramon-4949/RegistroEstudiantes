package edu.ucne.registroestudiantes.data.repository

import edu.ucne.registroestudiantes.data.local.dao.AsignaturaDao
import edu.ucne.registroestudiantes.data.mapper.toDomain
import edu.ucne.registroestudiantes.data.mapper.toEntity
import edu.ucne.registroestudiantes.domain.model.Asignatura
import edu.ucne.registroestudiantes.domain.repository.AsignaturaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AsignaturaRepositoryImpl @Inject constructor(
    private val dao: AsignaturaDao
) : AsignaturaRepository {

    override fun observeAsignaturas(): Flow<List<Asignatura>> {
        return dao.getAll().map { listaEntities ->
            listaEntities.map { it.toDomain() }
        }
    }

    override suspend fun getAsignatura(id: Int): Asignatura? {
        return dao.find(id)?.toDomain()
    }

    override suspend fun upsert(asignatura: Asignatura) {
        dao.upsert(asignatura.toEntity())
    }

    override suspend fun delete(id: Int) {
        val entity = dao.find(id)
        entity?.let {
            dao.delete(it)
        }
    }

    override suspend fun findByNombre(nombre: String): Asignatura? {
        return dao.findByNombre(nombre)?.toDomain()
    }
}