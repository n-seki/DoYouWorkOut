package seki.com.doyouworkout.data.repository

import seki.com.doyouworkout.data.cache.DataCache
import seki.com.doyouworkout.data.db.AppDataBase
import seki.com.doyouworkout.data.db.mapper.WorkoutMapper
import seki.com.doyouworkout.ui.Training
import seki.com.doyouworkout.ui.Workout
import javax.inject.Inject

class WorkoutRepository
@Inject constructor(val db: AppDataBase, val mapper: WorkoutMapper, val cache: DataCache) {

    fun getDummyTraining(): List<Training> {
        return listOf(
                Training(id = 1, name =  "腕立て", isUsed = true, isDeleted = false),
                Training(id = 1, name =  "腹筋", isUsed = true, isDeleted = false),
                Training(id = 1, name =  "背筋", isUsed = true, isDeleted = false),
                Training(id = 1, name =  "スクワット", isUsed = true, isDeleted = false)
        )
    }

}