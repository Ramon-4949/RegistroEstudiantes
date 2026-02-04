package edu.ucne.registroestudiantes.presentation.penalidad_tipo.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registroestudiantes.domain.penalidad_tipo.usecase.DeleteTipoPenalidadUseCase
import edu.ucne.registroestudiantes.domain.penalidad_tipo.usecase.ObserveTiposPenalidadesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TipoPenalidadListViewModel @Inject constructor(
    private val observeTiposPenalidadesUseCase: ObserveTiposPenalidadesUseCase,
    private val deleteTipoPenalidadUseCase: DeleteTipoPenalidadUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TipoPenalidadListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadTipos()
    }

    fun onEvent(event: TipoPenalidadListUiEvent) {
        when(event) {
            is TipoPenalidadListUiEvent.Load -> loadTipos()
            is TipoPenalidadListUiEvent.OnSelectTipoToDelete -> {
                _uiState.update {
                    it.copy(tipoToDelete = event.tipo, showDeleteDialog = true)
                }
            }

            is TipoPenalidadListUiEvent.OnConfirmDelete -> {
                val tipo = _uiState.value.tipoToDelete
                if (tipo != null) {
                    viewModelScope.launch {
                        deleteTipoPenalidadUseCase(tipo.tipoId)
                        _uiState.update { it.copy(showDeleteDialog = false, tipoToDelete = null) }
                    }
                }
            }

            is TipoPenalidadListUiEvent.OnDismissDialog -> {
                _uiState.update { it.copy(showDeleteDialog = false, tipoToDelete = null) }
            }
        }
    }

    private fun loadTipos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            observeTiposPenalidadesUseCase().collectLatest { lista ->
                _uiState.update {
                    it.copy(
                        tiposPenalidades = lista,
                        isLoading = false
                    )
                }
            }
        }
    }
}