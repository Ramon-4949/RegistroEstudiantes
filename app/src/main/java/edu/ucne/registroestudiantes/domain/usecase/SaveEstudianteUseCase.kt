package edu.ucne.registroestudiantes.domain.usecase

import edu.ucne.registroestudiantes.domain.model.Estudiante
import edu.ucne.registroestudiantes.domain.repository.EstudianteRepository
import javax.inject.Inject

class SaveEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(estudiante: Estudiante) {
        repository.save(estudiante)
    }
}