package edu.ucne.registroestudiantes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.registroestudiantes.data.local.EstudianteDAO
import edu.ucne.registroestudiantes.data.local.EstudianteEntity
import edu.ucne.registroestudiantes.data.local.entities.AsignaturaEntity
import edu.ucne.registroestudiantes.data.local.dao.AsignaturaDao

@Database(
    entities = [
        EstudianteEntity::class,
        AsignaturaEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class EstudiantesDB : RoomDatabase() {
    abstract fun estudianteDao(): EstudianteDAO
    abstract fun asignaturaDao(): AsignaturaDao
}