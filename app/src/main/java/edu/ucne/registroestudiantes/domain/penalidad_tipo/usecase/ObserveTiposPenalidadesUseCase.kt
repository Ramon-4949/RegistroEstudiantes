package edu.ucne.registroestudiantes.domain.penalidad_tipo.usecase

import edu.ucne.registroestudiantes.domain.penalidad_tipo.repository.TipoPenalidadRepository
import edu.ucne.registroestudiantes.domain.penalidad_tipo.model.TipoPenalidad
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveTiposPenalidadesUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository
) {
    operator fun invoke(): Flow<List<TipoPenalidad>> = repository.observeTiposPenalidades()
}