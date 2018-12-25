package seki.com.doyouworkout.usecase

import dagger.Module
import dagger.Provides
import seki.com.doyouworkout.data.DateSupplier
import seki.com.doyouworkout.data.db.mapper.WorkoutMapper
import seki.com.doyouworkout.data.repository.Repository
import seki.com.doyouworkout.di.WorkoutRepository
import seki.com.doyouworkout.usecase.impl.*
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetWorkoutUseCase(
            @WorkoutRepository repository: Repository,
            mapper: WorkoutMapper
    ): GetWorkoutUseCase {
        return GetWorkoutUseCaseImp(repository, mapper)
    }

    @Singleton
    @Provides
    fun provideUpdateWorkoutUseCase(
            @WorkoutRepository repository: Repository
    ): UpdateWorkoutUseCase {
        return UpdateWorkoutUseCaseImp(repository)
    }

    @Singleton
    @Provides
    fun provideFetchTrainingUseCase(
            @WorkoutRepository repository: Repository
    ): FetchTrainingUseCase {
        return FetchTrainingUseCaseImp(repository)
    }

    @Singleton
    @Provides
    fun provideUpdateTrainingUseCase(
            @WorkoutRepository repository: Repository
    ): UpdateTrainingUseCase {
        return UpdateTrainingUseCaseImp(repository)
    }

    @Singleton
    @Provides
    fun provideGetOneDayWorkoutListUseCase(
            @WorkoutRepository repository: Repository,
            mapper: WorkoutMapper,
            dateSupplier: DateSupplier
    ): GetOneDayWorkoutListUseCase {
        return GetOneDayWorkoutListUseCaseImp(
                repository, mapper, dateSupplier
        )
    }
}