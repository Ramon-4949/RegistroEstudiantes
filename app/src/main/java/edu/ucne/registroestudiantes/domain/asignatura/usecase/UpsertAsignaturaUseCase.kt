package edu.ucne.registroestudiantes.domain.asignatura.usecase

import edu.ucne.registroestudiantes.domain.asignatura.repository.AsignaturaRepository
import edu.ucne.registroestudiantes.domain.asignatura.model.Asignatura
import javax.inject.Inject

class UpsertAsignaturaUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    suspend operator fun invoke(asignatura: Asignatura) = repository.upsert(asignatura)
}