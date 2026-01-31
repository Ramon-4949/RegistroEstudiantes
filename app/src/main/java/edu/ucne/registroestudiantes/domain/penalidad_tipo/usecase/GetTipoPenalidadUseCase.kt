package edu.ucne.registroestudiantes.domain.penalidad_tipo.usecase

import edu.ucne.registroestudiantes.domain.penalidad_tipo.repository.TipoPenalidadRepository
import edu.ucne.registroestudiantes.domain.penalidad_tipo.model.TipoPenalidad
import javax.inject.Inject

class GetTipoPenalidadUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository
) {
    suspend operator fun invoke(id: Int): TipoPenalidad? = repository.getTipoPenalidad(id)
    suspend operator fun invoke(nombre: String): TipoPenalidad? = repository.findByNombre(nombre)
}