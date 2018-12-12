package seki.com.doyouworkout.usecase

import dagger.Module
import dagger.Provides
import seki.com.doyouworkout.data.db.mapper.WorkoutMapper
import seki.com.doyouworkout.data.repository.WorkoutRepository
import seki.com.doyouworkout.usecase.impl.FetchTrainingUseCaseImp
import seki.com.doyouworkout.usecase.impl.GetWorkoutUseCaseImp
import seki.com.doyouworkout.usecase.impl.UpdateTrainingUseCaseImp
import seki.com.doyouworkout.usecase.impl.UpdateWorkoutUseCaseImp
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetWorkoutUseCase(
            repository: WorkoutRepository,
            mapper: WorkoutMapper
    ): GetWorkoutUseCase {
        return GetWorkoutUseCaseImp(repository, mapper)
    }

    @Singleton
    @Provides
    fun provideUpdateWorkoutUseCase(
            repository: WorkoutRepository
    ): UpdateWorkoutUseCase {
        return UpdateWorkoutUseCaseImp(repository)
    }

    @Singleton
    @Provides
    fun provideFetchTrainingUseCase(
            repository: WorkoutRepository
    ): FetchTrainingUseCase {
        return FetchTrainingUseCaseImp(repository)
    }

    @Singleton
    @Provides
    fun provideUpdateTrainingUseCase(
            repository: WorkoutRepository
    ): UpdateTrainingUseCase {
        return UpdateTrainingUseCaseImp(repository)
    }
}