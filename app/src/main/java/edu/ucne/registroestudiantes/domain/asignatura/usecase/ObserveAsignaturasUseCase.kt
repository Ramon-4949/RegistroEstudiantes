package edu.ucne.registroestudiantes.domain.asignatura.usecase

import edu.ucne.registroestudiantes.domain.asignatura.repository.AsignaturaRepository
import kotlinx.coroutines.flow.Flow
import edu.ucne.registroestudiantes.domain.asignatura.model.Asignatura
import javax.inject.Inject

class ObserveAsignaturasUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    operator fun invoke(): Flow<List<Asignatura>> = repository.observeAsignaturas()
}