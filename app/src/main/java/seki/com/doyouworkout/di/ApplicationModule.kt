package seki.com.doyouworkout.di

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import seki.com.doyouworkout.data.cache.DataCache
import seki.com.doyouworkout.data.db.AppDataBase
import seki.com.doyouworkout.data.db.mapper.WorkoutMapper
import seki.com.doyouworkout.data.repository.WorkoutRepository
import seki.com.doyouworkout.domain.TrainingDomain
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
    fun provideRepository(db: AppDataBase, mapper: WorkoutMapper, cache: DataCache) =
            WorkoutRepository(db, mapper, cache)

    @Singleton
    @Provides
    fun provideTrainingDomain(repository: WorkoutRepository, mapper: WorkoutMapper) =
            TrainingDomain(repository, mapper)
}