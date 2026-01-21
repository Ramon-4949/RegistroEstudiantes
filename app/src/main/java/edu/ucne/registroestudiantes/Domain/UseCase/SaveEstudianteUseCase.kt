package edu.ucne.registroestudiantes.Domain.UseCase

import edu.ucne.registroestudiantes.Domain.Model.Estudiante
import edu.ucne.registroestudiantes.Domain.Repository.EstudianteRepository
import javax.inject.Inject

class SaveEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(estudiante: Estudiante) {
        repository.save(estudiante)
    }
}