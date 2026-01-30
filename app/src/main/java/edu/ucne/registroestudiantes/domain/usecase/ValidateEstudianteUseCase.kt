package edu.ucne.registroestudiantes.domain.usecase

import edu.ucne.registroestudiantes.domain.repository.EstudianteRepository
import javax.inject.Inject

class ValidateEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    data class ValidationResult(
        val isValid: Boolean,
        val nombreError: String? = null,
        val emailError: String? = null,
        val edadError: String? = null
    )

    suspend operator fun invoke(
        nombre: String,
        email: String,
        edad: Int?,
        currentId: Int? = null
    ): ValidationResult {

        val nombreError = when {
            nombre.isBlank() -> "El nombre es obligatorio"
            else -> {
                val existe = repository.getByNombre(nombre)
                val isDuplicate = if (currentId != null) {
                    existe != null && existe.estudianteId != currentId
                } else {
                    existe != null
                }

                if (isDuplicate) "Este nombre ya existe en la base de datos" else null
            }
        }

        val emailError = if (email.isBlank()) "El email es obligatorio" else null

        val edadError = if (edad == null || edad <= 0) "La edad es obligatoria y mayor a 0" else null

        return ValidationResult(
            isValid = nombreError == null && emailError == null && edadError == null,
            nombreError = nombreError,
            emailError = emailError,
            edadError = edadError
        )
    }
}