package edu.ucne.registroestudiantes.Data.Module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.registroestudiantes.Data.BD.EstudiantesDB
import edu.ucne.registroestudiantes.Data.Local.EstudianteDAO
import edu.ucne.registroestudiantes.Data.Repository.EstudianteRepositoryImpl
import edu.ucne.registroestudiantes.Domain.Repository.EstudianteRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideEstudianteDB(@ApplicationContext appContext: Context): EstudiantesDB {
        return Room.databaseBuilder(
            appContext,
            EstudiantesDB::class.java,
            "Estudiantes.db"
        )
            .fallbackToDestructiveMigration() // <--- CORREGIDO: Paréntesis vacíos
            .build()
    }

    @Provides
    @Singleton
    fun provideEstudianteDAO(db: EstudiantesDB): EstudianteDAO {
        return db.estudianteDao()
    }

    @Provides
    @Singleton
    fun provideEstudianteRepository(impl: EstudianteRepositoryImpl): EstudianteRepository {
        return impl
    }
}