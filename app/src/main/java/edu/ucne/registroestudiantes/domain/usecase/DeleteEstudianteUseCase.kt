package edu.ucne.registroestudiantes.domain.usecase

import edu.ucne.registroestudiantes.domain.repository.EstudianteRepository
import javax.inject.Inject

class DeleteEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(id: Int) {
        val estudiante = repository.find(id)
        if (estudiante != null) {
            repository.delete(estudiante)
        }
    }
}