package edu.ucne.registroestudiantes.domain.estudiantes.usecase

import edu.ucne.registroestudiantes.domain.estudiantes.model.Estudiante
import edu.ucne.registroestudiantes.domain.estudiantes.repository.EstudianteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveEstudiantesUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    operator fun invoke(): Flow<List<Estudiante>> {
        return repository.getAll()
    }
}