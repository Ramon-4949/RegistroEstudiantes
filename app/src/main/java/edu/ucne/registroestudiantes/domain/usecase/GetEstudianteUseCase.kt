package edu.ucne.registroestudiantes.domain.usecase

import edu.ucne.registroestudiantes.domain.repository.EstudianteRepository
import edu.ucne.registroestudiantes.domain.model.Estudiante
import javax.inject.Inject

class GetEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(id: Int): Estudiante? {
        return repository.find(id)
    }
}