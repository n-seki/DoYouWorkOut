package seki.com.doyouworkout.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import seki.com.doyouworkout.R
import seki.com.doyouworkout.data.cache.DataCache
import seki.com.doyouworkout.data.db.AppDataBase
import seki.com.doyouworkout.data.db.TrainingEntity
import seki.com.doyouworkout.data.db.WorkoutEntity
import seki.com.doyouworkout.ui.Training
import java.util.*
import javax.inject.Inject

class WorkoutRepository
@Inject constructor(db: AppDataBase, private val sharedPref: SharedPreferences, private val cache: DataCache) {

    private val trainingDao = db.trainingDao()
    private val workoutDao = db.workoutDao()

    companion object {
        const val KEY_INIT_APP = "key_init_app"

        private val DEFAULT_TRAINING =
                listOf(
                        TrainingEntity(id=0, trainingNameId = R.string.hukkin),
                        TrainingEntity(id=1, trainingNameId = R.string.udetate),
                        TrainingEntity(id=2, trainingNameId = R.string.haikin),
                        TrainingEntity(id=3, trainingNameId = R.string.squat)
                )
    }

    fun isInitApp(): Single<Boolean> {
        return Single.create<Boolean> { emitter ->
            if (sharedPref.getInt(KEY_INIT_APP, 0) == 1) {
                emitter.onSuccess(true)
            } else {
                emitter.onSuccess(false)
            }
        }
    }

    fun putDefaultTraining(): Completable {
        return Completable.fromAction {
            if (sharedPref.getInt(KEY_INIT_APP, 0) == 0) {
                trainingDao.insert(DEFAULT_TRAINING)
                sharedPref.edit {
                    putInt(KEY_INIT_APP, 1)
                }
            }
        }
    }

    fun getAllTrainingList(): Flowable<List<TrainingEntity>> {
        if (cache.hasTraining()) {
            return cache.getAllTraining()
        }

        return trainingDao.loadAll()
    }

    fun getUsedTrainingList(): Flowable<List<TrainingEntity>> {
        return getAllTrainingList().map { list ->
            list.filter { it.used }
        }
    }

    fun putTrainingCache(trainingList: List<TrainingEntity>) {
        cache.updateTraining(trainingList)
    }

    fun updateTraining(trainingList: List<TrainingEntity>): Completable {
        return Completable.fromAction {
            trainingDao.update(trainingList)
            cache.updateTraining(trainingList)
        }
    }

    fun getWorkout(date: Date): Single<List<WorkoutEntity>> {
        if (cache.hasWorkoutAt(date)) {
            return cache.getWorkoutAt(date)
        }

        return workoutDao.select(date)
    }

    fun updateWorkout(workoutEntities: List<WorkoutEntity>): Completable {
        return Completable.fromAction {
            workoutDao.insert(workoutEntities)
            cache.putWorkout(workoutEntities)
        }
    }

    fun getWorkoutList(): Single<List<WorkoutEntity>> {
        return workoutDao.load(Date())
    }
}

fun Training.toEntity() =
        TrainingEntity(
                id = id,
                trainingNameId = trainingNameId,
                used = isUsed,
                custom = isCustom,
                customName = customName,
                delete = isDeleted)
