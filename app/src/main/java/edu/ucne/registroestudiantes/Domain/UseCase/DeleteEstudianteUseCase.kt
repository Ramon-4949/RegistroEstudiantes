package edu.ucne.registroestudiantes.Domain.UseCase

import edu.ucne.registroestudiantes.Domain.Repository.EstudianteRepository
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