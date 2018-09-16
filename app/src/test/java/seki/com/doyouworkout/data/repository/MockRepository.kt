package seki.com.doyouworkout.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import seki.com.doyouworkout.data.cache.Cache
import seki.com.doyouworkout.data.db.TrainingEntity
import seki.com.doyouworkout.data.db.WorkoutEntity
import seki.com.doyouworkout.ui.Training
import java.util.*

class MockRepository(private val cache: Cache): Repository {

    companion object {
        val defaultTraining =
                listOf(
                        TrainingEntity(id=0, name = "腹筋"),
                        TrainingEntity(id=1, name = "腕立て伏せ"),
                        TrainingEntity(id=2, name = "背筋"),
                        TrainingEntity(id=3, name = "スクワット")
                )
    }

    override fun isInitApp(): Single<Boolean> {
        return Single.create { emitter -> emitter.onSuccess(true) }
    }

    override fun putDefaultTraining(): Completable {
        return Completable.complete()
    }

    override fun getAllTrainingList(): Single<List<TrainingEntity>> {
        return Single.create { emitter -> emitter.onSuccess(defaultTraining) }
    }

    override fun getUsedTrainingList(): Single<List<TrainingEntity>> {
        return Single.create { emitter -> emitter.onSuccess(defaultTraining) }
    }

    override fun putTrainingCache(trainingList: List<TrainingEntity>) {
        cache.updateTraining(trainingList)
    }

    override fun updateTraining(trainingList: List<Training>): Completable {
        return Completable.fromAction {
            val list = trainingList.map { it.toEntity() }
            cache.updateTraining(list)
        }
    }

    override fun getWorkout(date: Date): Single<List<WorkoutEntity>> {
        return Single.create { emitter ->
            emitter.onSuccess(
                    listOf(
                            WorkoutEntity(Date(), 1, 1)
                    )) }
    }

    override fun updateWorkout(workoutEntities: List<WorkoutEntity>): Completable {
        return Completable.fromAction { cache.putWorkout(workoutEntities) }
    }

    override fun getWorkoutList(limit: Int): Single<List<WorkoutEntity>> {
        return Single.create { emitter ->
            emitter.onSuccess(
                    listOf(WorkoutEntity(Date().previousDay(), 1, 1))
            )
        }
    }

    private fun Date.previousDay(): Date {
        val calendar = Calendar.getInstance().apply { time = this@previousDay }
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        return calendar.time
    }

}