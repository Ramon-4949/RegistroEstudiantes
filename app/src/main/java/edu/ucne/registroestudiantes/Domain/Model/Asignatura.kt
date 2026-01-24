package edu.ucne.registroestudiantes.Domain.Model

data class Asignatura(
    val asignaturaId: Int = 0,
    val codigo: String = "",
    val nombre: String = "",
    val aula: String = "",
    val creditos: Int = 0
)