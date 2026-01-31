package edu.ucne.registroestudiantes.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.registroestudiantes.data.db.EstudiantesDB
import edu.ucne.registroestudiantes.data.local.dao.EstudianteDAO
import edu.ucne.registroestudiantes.data.local.dao.AsignaturaDao
import edu.ucne.registroestudiantes.data.local.dao.TipoPenalidadDao
import edu.ucne.registroestudiantes.data.repository.AsignaturaRepositoryImpl
import edu.ucne.registroestudiantes.data.repository.EstudianteRepositoryImpl
import edu.ucne.registroestudiantes.data.repository.TipoPenalidadRepositoryImpl
import edu.ucne.registroestudiantes.domain.asignatura.repository.AsignaturaRepository
import edu.ucne.registroestudiantes.domain.estudiantes.repository.EstudianteRepository
import edu.ucne.registroestudiantes.domain.penalidad_tipo.repository.TipoPenalidadRepository
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
            .fallbackToDestructiveMigration()
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

    @Provides
    @Singleton
    fun provideAsignaturaDAO(db: EstudiantesDB): AsignaturaDao {
        return db.asignaturaDao()
    }

    @Provides
    @Singleton
    fun provideAsignaturaRepository(impl: AsignaturaRepositoryImpl): AsignaturaRepository {
        return impl
    }

    @Provides
    @Singleton
    fun provideTipoPenalidadDAO(db: EstudiantesDB): TipoPenalidadDao {
        return db.tipoPenalidadDao()
    }

    @Provides
    @Singleton
    fun provideTipoPenalidadRepository(impl: TipoPenalidadRepositoryImpl): TipoPenalidadRepository {
        return impl
    }
}