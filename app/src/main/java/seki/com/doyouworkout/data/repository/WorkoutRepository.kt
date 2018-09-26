package seki.com.doyouworkout.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import seki.com.doyouworkout.data.cache.Cache
import seki.com.doyouworkout.data.db.TrainingEntity
import seki.com.doyouworkout.data.db.WorkoutEntity
import seki.com.doyouworkout.ui.Training
import java.util.*
import javax.inject.Inject

class WorkoutRepository
@Inject constructor(
        private val localRepository: LocalRepository,
        private val cache: Cache): Repository {

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

        return localRepository.selectTraining()
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
        return Completable.fromAction {
            val list = trainingList.map { it.toEntity() }
            localRepository.insertTraining(list)
            cache.updateTraining(list)
        }
    }

    override fun getWorkout(date: Date): Single<List<WorkoutEntity>> {
        if (cache.hasWorkoutAt(date)) {
            return cache.getWorkoutAt(date)
        }

        return localRepository.selectWorkoutAt(date)
    }

    override fun updateWorkout(workoutEntities: List<WorkoutEntity>): Completable {
        return Completable.fromAction {
            localRepository.insertWorkout(workoutEntities)
            cache.putWorkout(workoutEntities)
        }
    }

    override fun getWorkoutList(limit: Int): Single<List<WorkoutEntity>> {
        return localRepository.selectWorkoutUntil(Date(), limit)
    }
}

fun Training.toEntity() =
        TrainingEntity(
                id = id,
                used = isUsed,
                custom = isCustom,
                name = name,
                delete = isDeleted)
