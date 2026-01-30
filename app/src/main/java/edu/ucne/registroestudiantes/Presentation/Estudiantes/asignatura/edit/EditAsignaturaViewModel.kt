package edu.ucne.registroestudiantes.Presentation.estudiantes.asignatura.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registroestudiantes.domain.model.Asignatura
import edu.ucne.registroestudiantes.domain.repository.AsignaturaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditAsignaturaViewModel @Inject constructor(
    private val repository: AsignaturaRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditAsignaturaUiState())
    val uiState = _uiState.asStateFlow()

    private val nombreRegex = Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9 ]*$")

    private val asignaturaId: Int = savedStateHandle.get<Int>("asignaturaId") ?: 0

    init {
        if (asignaturaId > 0) {
            getAsignatura(asignaturaId)
        }
    }

    fun onEvent(event: EditAsignaturaUiEvent) {
        when (event) {
            is EditAsignaturaUiEvent.CodigoChanged -> {
                _uiState.update { it.copy(codigo = event.codigo, codigoError = null) }
            }
            is EditAsignaturaUiEvent.NombreChanged -> {
                _uiState.update { it.copy(nombre = event.nombre, nombreError = null) }
            }
            is EditAsignaturaUiEvent.AulaChanged -> {
                _uiState.update { it.copy(aula = event.aula, aulaError = null) }
            }
            is EditAsignaturaUiEvent.CreditosChanged -> {
                if (event.creditos.all { it.isDigit() }) {
                    _uiState.update { it.copy(creditos = event.creditos, creditosError = null) }
                }
            }
            is EditAsignaturaUiEvent.Save -> {
                save()
            }
            is EditAsignaturaUiEvent.Nuevo -> {
                _uiState.update { EditAsignaturaUiState() }
            }
        }
    }

    private fun getAsignatura(id: Int) {
        viewModelScope.launch {
            val asignatura = repository.getAsignatura(id)
            if (asignatura != null) {
                _uiState.update {
                    it.copy(
                        asignaturaId = asignatura.asignaturaId,
                        codigo = asignatura.codigo,
                        nombre = asignatura.nombre,
                        aula = asignatura.aula,
                        creditos = asignatura.creditos.toString()
                    )
                }
            }
        }
    }

    private fun save() {
        viewModelScope.launch {
            if (isValid()) {
                repository.upsert(
                    Asignatura(
                        asignaturaId = asignaturaId,
                        codigo = _uiState.value.codigo.trim(),
                        nombre = _uiState.value.nombre.trim(),
                        aula = _uiState.value.aula.trim(),
                        creditos = _uiState.value.creditos.toIntOrNull() ?: 0
                    )
                )
                _uiState.update { it.copy(saved = true) }
            }
        }
    }

    private suspend fun isValid(): Boolean {
        var valid = true
        val state = _uiState.value
        val codigoLimpio = state.codigo.trim()
        val nombreLimpio = state.nombre.trim()
        val aulaLimpia = state.aula.trim()

        if (codigoLimpio.isBlank()) {
            _uiState.update { it.copy(codigoError = "Campo requerido") }
            valid = false
        }

        if (nombreLimpio.isBlank()) {
            _uiState.update { it.copy(nombreError = "Campo requerido") }
            valid = false
        } else if (!nombreLimpio.matches(nombreRegex)) {
            _uiState.update { it.copy(nombreError = "No caracteres especiales") }
            valid = false
        }

        if (aulaLimpia.isBlank()) {
            _uiState.update { it.copy(aulaError = "Campo requerido") }
            valid = false
        }

        if (state.creditos.isBlank() || (state.creditos.toIntOrNull() ?: 0) <= 0) {
            _uiState.update { it.copy(creditosError = "Debe ser > 0") }
            valid = false
        }

        if (valid) {
            val existe = repository.findByNombre(nombreLimpio)
            if (existe != null && existe.asignaturaId != asignaturaId) {
                _uiState.update { it.copy(nombreError = "Nombre ya existe") }
                valid = false
            }
        }
        return valid
    }
}