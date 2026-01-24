package edu.ucne.registroestudiantes.Presentation.Estudiantes.Asignatura

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registroestudiantes.Domain.Model.Asignatura
import edu.ucne.registroestudiantes.Domain.Repository.AsignaturaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AsignaturaUiState(
    val asignaturaId: Int? = null,
    val codigo: String = "",
    val codigoError: String? = null,
    val nombre: String = "",
    val nombreError: String? = null,
    val aula: String = "",
    val aulaError: String? = null,
    val creditos: String = "",
    val creditosError: String? = null,
    val saved: Boolean = false
)

@HiltViewModel
class AsignaturaViewModel @Inject constructor(
    private val repository: AsignaturaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AsignaturaUiState())
    val uiState = _uiState.asStateFlow()

    private val nombreRegex = Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9 ]*$")

    fun onCodigoChange(codigo: String) {
        _uiState.update { it.copy(codigo = codigo, codigoError = null) }
    }

    fun onNombreChange(nombre: String) {
        _uiState.update { it.copy(nombre = nombre, nombreError = null) }
    }

    fun onAulaChange(aula: String) {
        _uiState.update { it.copy(aula = aula, aulaError = null) }
    }

    fun onCreditosChange(creditos: String) {
        if (creditos.all { it.isDigit() }) {
            _uiState.update { it.copy(creditos = creditos, creditosError = null) }
        }
    }

    fun getAsignatura(id: Int) {
        if (id > 0) {
            viewModelScope.launch {
                val asignatura = repository.find(id)
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
    }

    fun save() {
        viewModelScope.launch {
            if (isValid()) {
                repository.save(
                    Asignatura(
                        asignaturaId = _uiState.value.asignaturaId ?: 0,
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

    fun nuevo() {
        _uiState.update { AsignaturaUiState() }
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
            _uiState.update { it.copy(nombreError = "No se permiten caracteres especiales") }
            valid = false
        }

        if (aulaLimpia.isBlank()) {
            _uiState.update { it.copy(aulaError = "Campo requerido") }
            valid = false
        }

        if (state.creditos.isBlank() || (state.creditos.toIntOrNull() ?: 0) <= 0) {
            _uiState.update { it.copy(creditosError = "Debe ser mayor a 0") }
            valid = false
        }

        if (valid) {
            val existe = repository.findByNombre(nombreLimpio)
            if (existe != null && existe.asignaturaId != (state.asignaturaId ?: 0)) {
                _uiState.update { it.copy(nombreError = "Ya existe este nombre") }
                valid = false
            }
        }
        return valid
    }
}