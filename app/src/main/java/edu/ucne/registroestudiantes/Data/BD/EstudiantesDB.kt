package edu.ucne.registroestudiantes.Data.BD

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.registroestudiantes.Data.Local.EstudianteEntity
import edu.ucne.registroestudiantes.Data.Local.EstudianteDAO

@Database(
    entities = [EstudianteEntity::class], // Aquí definimos la tabla de Estudiantes
    version = 1,
    exportSchema = false
)
abstract class EstudiantesDB : RoomDatabase() {

    abstract fun estudianteDao(): EstudianteDAO
}