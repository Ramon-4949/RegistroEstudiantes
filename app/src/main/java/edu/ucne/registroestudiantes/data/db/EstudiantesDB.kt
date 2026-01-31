package edu.ucne.registroestudiantes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.registroestudiantes.data.local.dao.EstudianteDAO
import edu.ucne.registroestudiantes.data.local.entities.EstudianteEntity
import edu.ucne.registroestudiantes.data.local.dao.AsignaturaDao
import edu.ucne.registroestudiantes.data.local.dao.TipoPenalidadDao
import edu.ucne.registroestudiantes.data.local.entities.AsignaturaEntity
import edu.ucne.registroestudiantes.data.local.entities.TipoPenalidadEntity

@Database(
    entities = [
        EstudianteEntity::class,
        AsignaturaEntity::class,
        TipoPenalidadEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class EstudiantesDB : RoomDatabase() {
    abstract fun estudianteDao(): EstudianteDAO
    abstract fun asignaturaDao(): AsignaturaDao
    abstract fun tipoPenalidadDao(): TipoPenalidadDao
}