package edu.ucne.registroestudiantes.domain.penalidad_tipo.usecase

import edu.ucne.registroestudiantes.domain.penalidad_tipo.repository.TipoPenalidadRepository
import javax.inject.Inject

class DeleteTipoPenalidadUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository
) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}