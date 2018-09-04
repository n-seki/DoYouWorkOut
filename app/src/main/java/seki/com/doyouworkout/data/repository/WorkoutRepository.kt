package seki.com.doyouworkout.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import io.reactivex.Completable
import io.reactivex.Single
import seki.com.doyouworkout.R
import seki.com.doyouworkout.data.ResourceSupplier
import seki.com.doyouworkout.data.cache.DataCache
import seki.com.doyouworkout.data.db.AppDataBase
import seki.com.doyouworkout.data.db.TrainingEntity
import seki.com.doyouworkout.data.db.WorkoutEntity
import seki.com.doyouworkout.ui.Training
import java.util.*
import javax.inject.Inject

class WorkoutRepository
@Inject constructor(
        db: AppDataBase,
        private val sharedPref: SharedPreferences,
        private val cache: DataCache,
        resourceSupplier: ResourceSupplier) {

    private val trainingDao = db.trainingDao()
    private val workoutDao = db.workoutDao()

    private val defaultTraining =
            listOf(
                    TrainingEntity(id=0, name = resourceSupplier.getString(R.string.hukkin)),
                    TrainingEntity(id=1, name = resourceSupplier.getString(R.string.udetate)),
                    TrainingEntity(id=2, name = resourceSupplier.getString(R.string.haikin)),
                    TrainingEntity(id=3, name = resourceSupplier.getString(R.string.squat))
            )

    companion object {
        const val KEY_INIT_APP = "key_init_app"
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
                trainingDao.insert(defaultTraining)
                sharedPref.edit {
                    putInt(KEY_INIT_APP, 1)
                }
            }
        }
    }

    fun getAllTrainingList(): Single<List<TrainingEntity>> {
        if (cache.hasTraining()) {
            return cache.getAllTraining()
        }

        return trainingDao.select()
    }

    fun getUsedTrainingList(): Single<List<TrainingEntity>> {
        return getAllTrainingList().map { list ->
            list.filter { it.used }
        }
    }

    fun putTrainingCache(trainingList: List<TrainingEntity>) {
        cache.updateTraining(trainingList)
    }

    fun updateTraining(trainingList: List<Training>): Completable {
        return Completable.fromAction {
            val list = trainingList.map { it.toEntity() }
            trainingDao.insert(list)
            cache.updateTraining(list)
        }
    }

    fun getWorkout(date: Date): Single<List<WorkoutEntity>> {
        if (cache.hasWorkoutAt(date)) {
            return cache.getWorkoutAt(date)
        }

        return workoutDao.selectAt(date)
    }

    fun updateWorkout(workoutEntities: List<WorkoutEntity>): Completable {
        return Completable.fromAction {
            workoutDao.insert(workoutEntities)
            cache.putWorkout(workoutEntities)
        }
    }

    fun getWorkoutList(): Single<List<WorkoutEntity>> {
        return workoutDao.selectUntil(Date())
    }

//    fun getWorkoutList(today: Date): Single<List<WorkoutEntity>> {
//        return workoutDao.insertAndSelect(today)
//    }

    fun insertEmptyWorkoutDataUntil(date: Date, trainingList: List<Training>) {

    }
}

fun Training.toEntity() =
        TrainingEntity(
                id = id,
                used = isUsed,
                custom = isCustom,
                name = name,
                delete = isDeleted)
