package edu.ucne.registroestudiantes.Domain.UseCase

import edu.ucne.registroestudiantes.Domain.Repository.EstudianteRepository
import edu.ucne.registroestudiantes.Domain.Model.Estudiante
import javax.inject.Inject

class GetEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(id: Int): Estudiante? {
        return repository.find(id)
    }
}