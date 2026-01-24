package edu.ucne.registroestudiantes.Data.Repository

import edu.ucne.registroestudiantes.Data.Local.dao.AsignaturaDao
import edu.ucne.registroestudiantes.Data.Local.entities.AsignaturaEntity
import edu.ucne.registroestudiantes.Domain.Repository.AsignaturaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AsignaturaRepositoryImpl @Inject constructor(
    private val dao: AsignaturaDao
) : AsignaturaRepository {
    override suspend fun save(asignatura: AsignaturaEntity) = dao.save(asignatura)
    override suspend fun delete(asignatura: AsignaturaEntity) = dao.delete(asignatura)
    override suspend fun find(id: Int): AsignaturaEntity? = dao.find(id)
    override suspend fun findByNombre(nombre: String): AsignaturaEntity? = dao.findByNombre(nombre)
    override fun getAll(): Flow<List<AsignaturaEntity>> = dao.getAll()
}