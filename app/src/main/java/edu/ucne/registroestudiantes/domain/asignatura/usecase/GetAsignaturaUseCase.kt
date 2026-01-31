package edu.ucne.registroestudiantes.domain.asignatura.usecase

import edu.ucne.registroestudiantes.domain.asignatura.repository.AsignaturaRepository
import edu.ucne.registroestudiantes.domain.asignatura.model.Asignatura
import javax.inject.Inject

class GetAsignaturaUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    suspend operator fun invoke(id: Int): Asignatura? = repository.getAsignatura(id)
    suspend operator fun invoke(nombre: String): Asignatura? = repository.findByNombre(nombre)
}