package seki.com.doyouworkout.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import seki.com.doyouworkout.data.cache.DataCache
import seki.com.doyouworkout.data.db.AppDataBase
import seki.com.doyouworkout.data.db.TrainingEntity
import seki.com.doyouworkout.data.db.mapper.WorkoutMapper
import seki.com.doyouworkout.ui.Training
import seki.com.doyouworkout.ui.Workout
import javax.inject.Inject

class WorkoutRepository
@Inject constructor(db: AppDataBase, val mapper: WorkoutMapper, private val cache: DataCache) {

    private val trainingDao = db.trainingDao()

    fun getDummyTraining(): List<TrainingEntity> {
        if (cache.hasTraining()) {
            return cache.getAllTraining()
        }

        return listOf(
//                Training(id = 1, name =  "腕立て", isUsed = true, isDeleted = false),
//                Training(id = 1, name =  "腹筋", isUsed = true, isDeleted = false),
//                Training(id = 1, name =  "背筋", isUsed = true, isDeleted = false),
//                Training(id = 1, name =  "スクワット", isUsed = true, isDeleted = false)
        )
    }

    fun updateTraining(trainingList: List<Training>) {
        val trainingEntityList = trainingList.map { it.toEntity() }
        cache.updateTraining(trainingEntityList)
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
