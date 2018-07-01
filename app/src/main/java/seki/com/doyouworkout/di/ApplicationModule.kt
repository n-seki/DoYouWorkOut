package seki.com.doyouworkout.di

import android.arch.persistence.room.Room
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import seki.com.doyouworkout.data.cache.DataCache
import seki.com.doyouworkout.data.db.AppDataBase
import seki.com.doyouworkout.data.db.mapper.WorkoutMapper
import seki.com.doyouworkout.data.repository.WorkoutRepository
import seki.com.doyouworkout.usecase.TrainingUseCase
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
            WorkoutMapper(applicationContext)

    @Singleton
    @Provides
    fun provideCache()
            = DataCache()

    @Singleton
    @Provides
    fun provideRepository(db: AppDataBase, sharedPreferences: SharedPreferences, cache: DataCache) =
            WorkoutRepository(db, sharedPreferences, cache)

    @Singleton
    @Provides
    fun provideTrainingUseCase(repository: WorkoutRepository, mapper: WorkoutMapper) =
            TrainingUseCase(repository, mapper)

    @Singleton
    @Provides
    fun provideSharedPreference() =
            applicationContext.getSharedPreferences("workout", MODE_PRIVATE)
}