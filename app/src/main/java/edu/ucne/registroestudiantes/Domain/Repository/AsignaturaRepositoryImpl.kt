package edu.ucne.registroestudiantes.Data.Repository

import edu.ucne.registroestudiantes.Data.Local.dao.AsignaturaDao
import edu.ucne.registroestudiantes.Data.Mapper.toDomain
import edu.ucne.registroestudiantes.Data.Mapper.toEntity
import edu.ucne.registroestudiantes.Domain.Model.Asignatura
import edu.ucne.registroestudiantes.Domain.Repository.AsignaturaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AsignaturaRepositoryImpl @Inject constructor(
    private val dao: AsignaturaDao
) : AsignaturaRepository {

    override suspend fun save(asignatura: Asignatura) {
        dao.save(asignatura.toEntity())
    }

    override suspend fun delete(asignatura: Asignatura) {
        dao.delete(asignatura.toEntity())
    }

    override suspend fun find(id: Int): Asignatura? {
        return dao.find(id)?.toDomain()
    }

    override suspend fun findByNombre(nombre: String): Asignatura? {
        return dao.findByNombre(nombre)?.toDomain()
    }

    override fun getAll(): Flow<List<Asignatura>> {
        return dao.getAll().map { listaEntities ->
            listaEntities.map { it.toDomain() }
        }
    }
}