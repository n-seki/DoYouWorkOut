package seki.com.doyouworkout.data.repository.impl

import android.content.SharedPreferences
import androidx.core.content.edit
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import seki.com.doyouworkout.R
import seki.com.doyouworkout.data.db.AppDataBase
import seki.com.doyouworkout.data.db.entity.TrainingEntity
import seki.com.doyouworkout.data.db.entity.WorkoutEntity
import seki.com.doyouworkout.data.repository.Repository
import seki.com.doyouworkout.data.resource.ResourceSupplier
import seki.com.doyouworkout.ui.Training
import java.util.*
import javax.inject.Inject

class LocalRepositoryImp @Inject constructor(
        db: AppDataBase,
        private val sharedPref: SharedPreferences,
        resourceSupplier: ResourceSupplier
) : Repository {

    private val trainingDao = db.trainingDao()
    private val workoutDao = db.workoutDao()

    override fun getAllTrainingList(): Single<List<TrainingEntity>> {
        return trainingDao.select()
    }

    override fun getUsedTrainingList(): Single<List<TrainingEntity>> {
        return trainingDao.select().map { list -> list.filter { it.used } }
    }

    override fun putTrainingCache(trainingList: List<TrainingEntity>) {
        // do nothing
    }

    override fun updateTraining(trainingList: List<Training>): Completable {
        return Completable.fromAction {
            trainingDao.insert(trainingList.map { it.toEntity() })
        }
    }

    override fun getWorkout(date: Date): Single<List<WorkoutEntity>> {
        return workoutDao.selectAt(date)
    }

    override fun updateWorkout(workoutEntities: List<WorkoutEntity>): Completable {
        return Completable.fromAction { workoutDao.insert(workoutEntities) }
    }

    override fun getWorkoutList(date: Date, limit: Int): Single<List<WorkoutEntity>> {
        return workoutDao.selectUntil(date, limit)
    }

    override fun getLastWorkout(): Maybe<WorkoutEntity?> {
        return workoutDao.selectLatest()
    }

    private val defaultTraining =
            listOf(
                    TrainingEntity(id = 0, name = resourceSupplier.getString(R.string.hukkin)),
                    TrainingEntity(id = 1, name = resourceSupplier.getString(R.string.udetate)),
                    TrainingEntity(id = 2, name = resourceSupplier.getString(R.string.haikin)),
                    TrainingEntity(id = 3, name = resourceSupplier.getString(R.string.squat))
            )

    companion object {
        const val KEY_INIT_APP = "key_init_app"
    }

    override fun putDefaultTraining(): Completable {
        return Completable.fromAction {
            if (isInitApp()) {
                trainingDao.insert(defaultTraining)
                completeInitApp()
            }
        }
    }

    override fun isInitApp(): Boolean {
        return sharedPref.getBoolean(KEY_INIT_APP, false)
    }

    private fun completeInitApp() {
        sharedPref.edit {
            putBoolean(KEY_INIT_APP, true)
        }
    }
}