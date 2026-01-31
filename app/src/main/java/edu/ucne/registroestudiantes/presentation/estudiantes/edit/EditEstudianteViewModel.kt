package edu.ucne.registroestudiantes.presentation.estudiantes.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registroestudiantes.domain.estudiantes.model.Estudiante
import edu.ucne.registroestudiantes.domain.estudiantes.usecase.GetEstudianteUseCase
import edu.ucne.registroestudiantes.domain.estudiantes.usecase.SaveEstudianteUseCase
import edu.ucne.registroestudiantes.domain.estudiantes.usecase.ValidateEstudianteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditEstudianteViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val saveEstudianteUseCase: SaveEstudianteUseCase,
    private val validateEstudianteUseCase: ValidateEstudianteUseCase,
    private val getEstudianteUseCase: GetEstudianteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditEstudianteUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val estudianteId = savedStateHandle.get<Int>("estudianteId") ?: 0
            if (estudianteId != 0) {
                val estudiante = getEstudianteUseCase(estudianteId)
                if (estudiante != null) {
                    _uiState.update {
                        it.copy(
                            estudianteId = estudiante.estudianteId,
                            nombre = estudiante.nombres,
                            email = estudiante.email,
                            edad = estudiante.edad.toString()
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: EditEstudianteUiEvent) {
        when (event) {
            is EditEstudianteUiEvent.NombreChanged -> _uiState.update { it.copy(nombre = event.nombre, nombreError = null) }
            is EditEstudianteUiEvent.EmailChanged -> _uiState.update { it.copy(email = event.email, emailError = null) }
            is EditEstudianteUiEvent.EdadChanged -> _uiState.update { it.copy(edad = event.edad, edadError = null) }
            is EditEstudianteUiEvent.Save -> saveEstudiante()
            is EditEstudianteUiEvent.New -> _uiState.update { EditEstudianteUiState() }
        }
    }

    private fun saveEstudiante() {
        viewModelScope.launch {
            val edadInt = _uiState.value.edad.toIntOrNull()

            val validation = validateEstudianteUseCase(
                nombre = _uiState.value.nombre,
                email = _uiState.value.email,
                edad = edadInt,
                currentId = _uiState.value.estudianteId
            )

            if (!validation.isValid) {
                _uiState.update { state ->
                    state.copy(
                        nombreError = validation.nombreError,
                        emailError = validation.emailError,
                        edadError = validation.edadError
                    )
                }
                return@launch
            }

            saveEstudianteUseCase(
                Estudiante(
                    estudianteId = _uiState.value.estudianteId ?: 0,
                    nombres = _uiState.value.nombre,
                    email = _uiState.value.email,
                    edad = edadInt!!
                )
            )
            _uiState.update { it.copy(saved = true) }
        }
    }
}