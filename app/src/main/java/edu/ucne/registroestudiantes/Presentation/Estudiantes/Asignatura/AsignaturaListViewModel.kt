package edu.ucne.registroestudiantes.Presentation.Estudiantes.Asignatura

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registroestudiantes.Data.Local.entities.AsignaturaEntity
import edu.ucne.registroestudiantes.Domain.Repository.AsignaturaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
data class AsignaturaListUiState(
    val asignaturas: List<AsignaturaEntity> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class AsignaturaListViewModel @Inject constructor(
    private val repository: AsignaturaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AsignaturaListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getAsignaturas()
    }

    private fun getAsignaturas() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.getAll().collectLatest { lista ->
                _uiState.update {
                    it.copy(
                        asignaturas = lista,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun delete(asignatura: AsignaturaEntity) {
        viewModelScope.launch {
            repository.delete(asignatura)
        }
    }
}