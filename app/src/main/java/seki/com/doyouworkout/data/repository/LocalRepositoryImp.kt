package seki.com.doyouworkout.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import io.reactivex.Completable
import io.reactivex.Single
import seki.com.doyouworkout.R
import seki.com.doyouworkout.data.ResourceSupplier
import seki.com.doyouworkout.data.db.AppDataBase
import seki.com.doyouworkout.data.db.entity.TrainingEntity
import seki.com.doyouworkout.data.db.entity.WorkoutEntity
import java.util.*
import javax.inject.Inject

class LocalRepositoryImp
@Inject constructor(
        db: AppDataBase,
        private val sharedPref: SharedPreferences,
        resourceSupplier: ResourceSupplier): LocalRepository {

    private val trainingDao = db.trainingDao()
    private val workoutDao = db.workoutDao()

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
            if (getAppInitStatus() == 0) {
                insertTraining(defaultTraining)
                putAppInitStatus()
            }
        }
    }

    override fun insertTraining(trainingList: List<TrainingEntity>) {
        trainingDao.insert(trainingList)
    }

    override fun selectTraining(): Single<List<TrainingEntity>> {
        return trainingDao.select()
    }

    override fun selectWorkoutAt(date: Date): Single<List<WorkoutEntity>> {
        return workoutDao.selectAt(date)
    }

    override fun insertWorkout(workoutList: List<WorkoutEntity>) {
        workoutDao.insert(workoutList)
    }

    override fun selectWorkoutUntil(date: Date, limit: Int): Single<List<WorkoutEntity>> {
        return workoutDao.selectUntil(date, limit)
    }

    override fun isInitApp(): Single<Boolean> {
        return Single.create<Boolean> { emitter ->
            if (sharedPref.getInt(KEY_INIT_APP, 0) == 1) {
                emitter.onSuccess(true)
            } else {
                emitter.onSuccess(false)
            }
        }
    }

    private fun getAppInitStatus(): Int =
            sharedPref.getInt(KEY_INIT_APP, 0)

    private fun putAppInitStatus() {
        sharedPref.edit {
            putInt(KEY_INIT_APP, 1)
        }
    }
}