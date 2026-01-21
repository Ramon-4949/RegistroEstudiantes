package edu.ucne.registroestudiantes.Data.Repository

import edu.ucne.registroestudiantes.Data.Local.EstudianteDAO
import edu.ucne.registroestudiantes.Data.Mapper.toDomain
import edu.ucne.registroestudiantes.Data.Mapper.toEntity
import edu.ucne.registroestudiantes.Domain.Model.Estudiante
import edu.ucne.registroestudiantes.Domain.Repository.EstudianteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EstudianteRepositoryImpl @Inject constructor(
    private val dao: EstudianteDAO
) : EstudianteRepository {

    override fun getAll(): Flow<List<Estudiante>> {
        return dao.getAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun find(id: Int): Estudiante? {
        return dao.find(id)?.toDomain()
    }

    override suspend fun save(estudiante: Estudiante) {
        dao.save(estudiante.toEntity())
    }

    override suspend fun delete(estudiante: Estudiante) {
        dao.delete(estudiante.toEntity())
    }

    override suspend fun getByNombre(nombre: String): Estudiante? {
        return dao.getByNombre(nombre)?.toDomain()
    }
}