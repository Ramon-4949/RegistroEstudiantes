package edu.ucne.registroestudiantes.data.repository

import edu.ucne.registroestudiantes.data.local.dao.EstudianteDAO
import edu.ucne.registroestudiantes.data.mapper.toDomain
import edu.ucne.registroestudiantes.data.mapper.toEntity
import edu.ucne.registroestudiantes.domain.estudiantes.model.Estudiante
import edu.ucne.registroestudiantes.domain.estudiantes.repository.EstudianteRepository
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