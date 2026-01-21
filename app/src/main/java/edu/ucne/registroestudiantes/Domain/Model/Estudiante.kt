package edu.ucne.registroestudiantes.Domain.Model

data class Estudiante(
    val estudianteId: Int = 0,
    val nombres: String,
    val email: String,
    val edad: Int
)