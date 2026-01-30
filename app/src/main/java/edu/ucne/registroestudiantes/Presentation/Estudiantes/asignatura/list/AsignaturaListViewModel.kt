package edu.ucne.registroestudiantes.Presentation.estudiantes.asignatura.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registroestudiantes.domain.repository.AsignaturaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AsignaturaListViewModel @Inject constructor(
    private val repository: AsignaturaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AsignaturaListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        onEvent(AsignaturaListUiEvent.Load)
    }

    fun onEvent(event: AsignaturaListUiEvent) {
        when(event) {
            is AsignaturaListUiEvent.Load -> {
                getAsignaturas()
            }
            is AsignaturaListUiEvent.Delete -> {
                delete(event.asignaturaId)
            }
        }
    }

    private fun getAsignaturas() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.observeAsignaturas().collectLatest { lista ->
                _uiState.update {
                    it.copy(
                        asignaturas = lista,
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