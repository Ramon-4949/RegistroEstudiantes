package edu.ucne.registroestudiantes.domain.asignatura.usecase

import edu.ucne.registroestudiantes.domain.asignatura.repository.AsignaturaRepository
import javax.inject.Inject

class DeleteAsignaturaUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}