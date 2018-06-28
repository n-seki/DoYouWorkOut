package seki.com.doyouworkout.data.repository

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.android.synthetic.main.fragment_setting.view.*
import seki.com.doyouworkout.R
import seki.com.doyouworkout.data.cache.DataCache
import seki.com.doyouworkout.data.db.AppDataBase
import seki.com.doyouworkout.data.db.TrainingEntity
import seki.com.doyouworkout.data.db.mapper.WorkoutMapper
import seki.com.doyouworkout.ui.Training
import javax.inject.Inject

class WorkoutRepository
@Inject constructor(db: AppDataBase, val mapper: WorkoutMapper, private val cache: DataCache) {

    private val trainingDao = db.trainingDao()

    fun getAllTrainingList(): Single<List<TrainingEntity>> {
        if (cache.hasTraining()) {
            return cache.getAllTraining()
        }

        return trainingDao.loadAll().doOnSuccess {
            cache.putTraining(it)
        }
    }

    fun updateTraining(trainingList: List<TrainingEntity>): Completable = Completable.fromAction {
        trainingDao.update(trainingList)
        cache.updateTraining(trainingList)
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
