package edu.ucne.registroestudiantes.Data.Local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.registroestudiantes.Data.Local.entities.AsignaturaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AsignaturaDao {
    @Upsert
    suspend fun save(asignatura: AsignaturaEntity)

    @Delete
    suspend fun delete(asignatura: AsignaturaEntity)

    @Query("SELECT * FROM asignaturas WHERE asignaturaId = :id")
    suspend fun find(id: Int): AsignaturaEntity?

    @Query("SELECT * FROM asignaturas")
    fun getAll(): Flow<List<AsignaturaEntity>>

    @Query("SELECT * FROM asignaturas WHERE nombre = :nombre LIMIT 1")
    suspend fun findByNombre(nombre: String): AsignaturaEntity?
}