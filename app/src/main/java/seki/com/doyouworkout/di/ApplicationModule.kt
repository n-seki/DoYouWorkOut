package seki.com.doyouworkout.di

import android.arch.persistence.room.Room
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import seki.com.doyouworkout.data.ResourceSupplier
import seki.com.doyouworkout.data.ResourceSupplierImp
import seki.com.doyouworkout.data.cache.DataCache
import seki.com.doyouworkout.data.db.AppDataBase
import seki.com.doyouworkout.data.db.mapper.WorkoutMapper
import seki.com.doyouworkout.data.repository.LocalRepository
import seki.com.doyouworkout.data.repository.LocalRepositoryImp
import seki.com.doyouworkout.data.repository.WorkoutRepository
import seki.com.doyouworkout.usecase.*
import seki.com.doyouworkout.usecase.impl.GetWorkoutUseCaseImp
import seki.com.doyouworkout.usecase.impl.UpdateWorkoutUseCaseImp
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
    fun provideRepository(localRepository: LocalRepository, cache: DataCache) =
            WorkoutRepository(localRepository, cache)

    @Singleton
    @Provides
    fun provideLocalRepository(db: AppDataBase, sharedPreferences: SharedPreferences, resourceSupplier: ResourceSupplier): LocalRepository =
            LocalRepositoryImp(db, sharedPreferences, resourceSupplier)

    @Singleton
    @Provides
    fun provideTrainingUseCase(repository: WorkoutRepository, schedulersProvider: SchedulersProviderBase) =
            TrainingUseCase(repository, schedulersProvider)

    @Singleton
    @Provides
    fun provideWorkoutUseCase(
            repository: WorkoutRepository,
            mapper: WorkoutMapper,
            schedulersProvider: SchedulersProviderBase
    ): WorkoutUseCase {
        return WorkoutUseCase(repository, mapper, schedulersProvider)
    }

    @Singleton
    @Provides
    fun provideSharedPreference(): SharedPreferences =
            applicationContext.getSharedPreferences("workout", MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideResourceSupplier(): ResourceSupplier =
            ResourceSupplierImp(applicationContext)

    @Provides
    fun provideSchedulerProvider(): SchedulersProviderBase {
        return SchedulersProvider
    }

    @Provides
    fun provideGetWorkoutUseCase(
            repository: WorkoutRepository,
            mapper: WorkoutMapper
    ): GetWorkoutUseCase {
        return GetWorkoutUseCaseImp(repository, mapper)
    }

    @Provides
    fun provideUpdateWorkoutUseCase(
            repository: WorkoutRepository
    ): UpdateWorkoutUseCase {
        return UpdateWorkoutUseCaseImp(repository)
    }
}