package edu.ucne.registroestudiantes.presentation.penalidad_tipo.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registroestudiantes.domain.penalidad_tipo.model.TipoPenalidad
import edu.ucne.registroestudiantes.domain.penalidad_tipo.usecase.GetTipoPenalidadUseCase
import edu.ucne.registroestudiantes.domain.penalidad_tipo.usecase.UpsertTipoPenalidadUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTipoPenalidadViewModel @Inject constructor(
    private val getTipoPenalidadUseCase: GetTipoPenalidadUseCase,
    private val upsertTipoPenalidadUseCase: UpsertTipoPenalidadUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditTipoPenalidadUiState())
    val uiState = _uiState.asStateFlow()

    private val tipoId: Int = savedStateHandle.get<Int>("tipoId") ?: 0

    init {
        if (tipoId > 0) {
            getTipoPenalidad(tipoId)
        }
    }

    fun onEvent(event: EditTipoPenalidadUiEvent) {
        when (event) {
            is EditTipoPenalidadUiEvent.NombreChanged -> {
                _uiState.update { it.copy(nombre = event.nombre, nombreError = null) }
            }
            is EditTipoPenalidadUiEvent.DescripcionChanged -> {
                _uiState.update { it.copy(descripcion = event.descripcion, descripcionError = null) }
            }
            is EditTipoPenalidadUiEvent.PuntosChanged -> {
                if (event.puntos.all { it.isDigit() }) {
                    _uiState.update { it.copy(puntosDescuento = event.puntos, puntosError = null) }
                }
            }
            is EditTipoPenalidadUiEvent.Save -> {
                save()
            }
            is EditTipoPenalidadUiEvent.Nuevo -> {
                _uiState.update { EditTipoPenalidadUiState() }
            }
            is EditTipoPenalidadUiEvent.OnSaved -> {
                _uiState.update { it.copy(saved = false) }
            }
        }
    }

    private fun getTipoPenalidad(id: Int) {
        viewModelScope.launch {
            val tipo = getTipoPenalidadUseCase(id)
            tipo?.let {
                _uiState.update { state ->
                    state.copy(
                        tipoId = it.tipoId,
                        nombre = it.nombre,
                        descripcion = it.descripcion,
                        puntosDescuento = it.puntosDescuento.toString()
                    )
                }
            }
        }
    }

    private fun save() {
        viewModelScope.launch {
            if (isValid()) {
                upsertTipoPenalidadUseCase(
                    TipoPenalidad(
                        tipoId = tipoId,
                        nombre = _uiState.value.nombre.trim(),
                        descripcion = _uiState.value.descripcion.trim(),
                        puntosDescuento = _uiState.value.puntosDescuento.toIntOrNull() ?: 0
                    )
                )
                _uiState.update { it.copy(saved = true) }
            }
        }
    }

    private suspend fun isValid(): Boolean {
        var valid = true
        val state = _uiState.value

        if (state.nombre.isBlank()) {
            _uiState.update { it.copy(nombreError = "Requerido") }
            valid = false
        }

        if (state.descripcion.isBlank()) {
            _uiState.update { it.copy(descripcionError = "Requerido") }
            valid = false
        }

        val puntos = state.puntosDescuento.toIntOrNull() ?: 0
        if (puntos <= 0) {
            _uiState.update { it.copy(puntosError = "> 0") }
            valid = false
        }

        if (valid) {
            val existe = getTipoPenalidadUseCase(state.nombre.trim())
            if (existe != null && existe.tipoId != tipoId) {
                _uiState.update { it.copy(nombreError = "Ya existe") }
                valid = false
            }
        }
        return valid
    }
}