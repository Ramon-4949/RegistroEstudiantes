package edu.ucne.registroestudiantes.presentation.estudiantes.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registroestudiantes.domain.estudiantes.repository.EstudianteRepository
import edu.ucne.registroestudiantes.domain.estudiantes.usecase.DeleteEstudianteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EstudianteListViewModel @Inject constructor(
    private val estudianteRepository: EstudianteRepository,
    private val deleteEstudianteUseCase: DeleteEstudianteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EstudianteListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getEstudiantes()
    }

    private fun getEstudiantes() {
        viewModelScope.launch {
            estudianteRepository.getAll().collect { estudiantes ->
                _uiState.update { it.copy(estudiantes = estudiantes) }
            }
        }
    }

    fun onEvent(event: EstudianteListUiEvent) {
        when(event) {
            is EstudianteListUiEvent.Delete -> {
                viewModelScope.launch {
                    deleteEstudianteUseCase(event.estudianteId)
                }
            }
        }
    }
}

sealed interface EstudianteListUiEvent {
    data class Delete(val estudianteId: Int) : EstudianteListUiEvent
}