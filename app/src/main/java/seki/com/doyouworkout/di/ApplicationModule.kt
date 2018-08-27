package seki.com.doyouworkout.di

import android.arch.persistence.room.Room
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import seki.com.doyouworkout.data.ResourceSupplier
import seki.com.doyouworkout.data.cache.DataCache
import seki.com.doyouworkout.data.db.AppDataBase
import seki.com.doyouworkout.data.db.mapper.WorkoutMapper
import seki.com.doyouworkout.data.repository.WorkoutRepository
import seki.com.doyouworkout.usecase.TrainingUseCase
import seki.com.doyouworkout.usecase.WorkoutUseCase
import javax.inject.Singleton

@Module
class ApplicationModule(private val applicationContext: Context) {

    @Singleton
    @Provides
    fun provideDataBase() =
            Room.databaseBuilder(applicationContext, AppDataBase::class.java, "workout").build()

    @Singleton
    @Provides
    fun provideEntityMapper() =
            WorkoutMapper()

    @Singleton
    @Provides
    fun provideCache()
            = DataCache()

    @Singleton
    @Provides
    fun provideRepository(db: AppDataBase, sharedPreferences: SharedPreferences, cache: DataCache, resourceSupplier: ResourceSupplier) =
            WorkoutRepository(db, sharedPreferences, cache, resourceSupplier)

    @Singleton
    @Provides
    fun provideTrainingUseCase(repository: WorkoutRepository) =
            TrainingUseCase(repository)

    @Singleton
    @Provides
    fun provideWorkoutUseCase(repository: WorkoutRepository, mapper: WorkoutMapper) =
            WorkoutUseCase(repository, mapper)

    @Singleton
    @Provides
    fun provideSharedPreference(): SharedPreferences =
            applicationContext.getSharedPreferences("workout", MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideResourceSupplier(): ResourceSupplier =
            ResourceSupplier(applicationContext)
}