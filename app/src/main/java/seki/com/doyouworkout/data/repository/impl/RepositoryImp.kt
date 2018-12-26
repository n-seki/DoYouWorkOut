package seki.com.doyouworkout.data.repository.impl

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import seki.com.doyouworkout.data.cache.Cache
import seki.com.doyouworkout.data.db.entity.TrainingEntity
import seki.com.doyouworkout.data.db.entity.WorkoutEntity
import seki.com.doyouworkout.data.repository.Repository
import seki.com.doyouworkout.ui.Training
import java.util.*
import javax.inject.Inject

class RepositoryImp @Inject constructor(
        private val localRepository: Repository,
        private val cache: Cache
) : Repository {

    override fun isInitApp(): Single<Boolean> {
        return localRepository.isInitApp()
    }

    override fun putDefaultTraining(): Completable {
        return localRepository.putDefaultTraining()
    }

    override fun getAllTrainingList(): Single<List<TrainingEntity>> {
        if (cache.hasTraining()) {
            return cache.getAllTraining()
        }

        return localRepository.getAllTrainingList()
    }

    override fun getUsedTrainingList(): Single<List<TrainingEntity>> {
        return getAllTrainingList().map { list ->
            list.filter { it.used }
        }
    }

    override fun putTrainingCache(trainingList: List<TrainingEntity>) {
        cache.updateTraining(trainingList)
    }

    override fun updateTraining(trainingList: List<Training>): Completable {
        val updateTraining =
                localRepository.updateTraining(trainingList)

        val updateCache = Completable.fromAction {
            val list = trainingList.map { it.toEntity() }
            cache.updateTraining(list)
        }

        return updateTraining.andThen(updateCache)
    }

    override fun getWorkout(date: Date): Single<List<WorkoutEntity>> {
        if (cache.hasWorkoutAt(date)) {
            return cache.getWorkoutAt(date)
        }

        return localRepository.getWorkout(date)
    }

    override fun updateWorkout(workoutEntities: List<WorkoutEntity>): Completable {
        val updateWorkout = localRepository.updateWorkout(workoutEntities)
        val updateCache = Completable.fromAction {
            cache.putWorkout(workoutEntities)
        }

        return updateWorkout.andThen(updateCache)
    }

    override fun getWorkoutList(date: Date, limit: Int): Single<List<WorkoutEntity>> {
        return localRepository.getWorkoutList(date, limit)
    }

    override fun getLastWorkout(): Maybe<WorkoutEntity?> {
        return localRepository.getLastWorkout()
    }
}

fun Training.toEntity() =
        TrainingEntity(
                id = id,
                used = isUsed,
                custom = isCustom,
                name = name,
                delete = isDeleted)
