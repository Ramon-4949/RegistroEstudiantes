package edu.ucne.registroestudiantes.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.registroestudiantes.data.local.entities.EstudianteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EstudianteDAO {
    @Upsert
    suspend fun save(estudiante: EstudianteEntity)
    @Delete
    suspend fun delete(estudiante: EstudianteEntity)
    @Query("SELECT * FROM estudiantes WHERE estudianteId = :id LIMIT 1")
    suspend fun find(id: Int): EstudianteEntity?
    @Query("SELECT * FROM estudiantes ORDER BY estudianteId DESC")
    fun getAll(): Flow<List<EstudianteEntity>>
    @Query("SELECT * FROM estudiantes WHERE nombres = :nombre LIMIT 1")
    suspend fun getByNombre(nombre: String): EstudianteEntity?
}