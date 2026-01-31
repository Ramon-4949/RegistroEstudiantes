package edu.ucne.registroestudiantes.presentation.penalidad_tipo.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registroestudiantes.domain.penalidad_tipo.repository.TipoPenalidadRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TipoPenalidadListViewModel @Inject constructor(
    private val repository: TipoPenalidadRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TipoPenalidadListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        onEvent(TipoPenalidadListUiEvent.Load)
    }

    fun onEvent(event: TipoPenalidadListUiEvent) {
        when(event) {
            is TipoPenalidadListUiEvent.Load -> {
                getTiposPenalidades()
            }
            is TipoPenalidadListUiEvent.Delete -> {
                delete(event.tipoId)
            }
        }
    }

    private fun getTiposPenalidades() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.observeTiposPenalidades().collectLatest { lista ->
                _uiState.update {
                    it.copy(
                        tiposPenalidades = lista,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun delete(id: Int) {
        viewModelScope.launch {
            repository.delete(id)
        }
    }
}