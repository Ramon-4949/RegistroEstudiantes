package edu.ucne.registroestudiantes.Data.BD

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.registroestudiantes.Data.Local.EstudianteDAO
import edu.ucne.registroestudiantes.Data.Local.EstudianteEntity
import edu.ucne.registroestudiantes.Data.Local.entities.AsignaturaEntity
import edu.ucne.registroestudiantes.Data.Local.dao.AsignaturaDao

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