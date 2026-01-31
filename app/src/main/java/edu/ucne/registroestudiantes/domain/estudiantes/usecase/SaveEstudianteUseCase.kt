package edu.ucne.registroestudiantes.domain.estudiantes.usecase

import edu.ucne.registroestudiantes.domain.estudiantes.model.Estudiante
import edu.ucne.registroestudiantes.domain.estudiantes.repository.EstudianteRepository
import javax.inject.Inject

class SaveEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(estudiante: Estudiante) {
        repository.save(estudiante)
    }
}