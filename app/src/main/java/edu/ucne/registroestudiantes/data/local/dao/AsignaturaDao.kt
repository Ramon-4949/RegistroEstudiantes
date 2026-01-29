package edu.ucne.registroestudiantes.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.registroestudiantes.data.local.entities.AsignaturaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AsignaturaDao {
    @Upsert
    suspend fun upsert(asignatura: AsignaturaEntity) // Renombrando de save a upsert

    @Delete
    suspend fun delete(asignatura: AsignaturaEntity)

    @Query("SELECT * FROM asignaturas WHERE asignaturaId = :id")
    suspend fun find(id: Int): AsignaturaEntity?

    @Query("SELECT * FROM asignaturas")
    fun getAll(): Flow<List<AsignaturaEntity>>

    @Query("SELECT * FROM asignaturas WHERE nombre = :nombre LIMIT 1")
    suspend fun findByNombre(nombre: String): AsignaturaEntity?
}