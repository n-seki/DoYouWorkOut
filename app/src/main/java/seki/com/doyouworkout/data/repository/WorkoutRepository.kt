package seki.com.doyouworkout.data.repository

import seki.com.doyouworkout.data.cache.DataCache
import seki.com.doyouworkout.data.db.AppDataBase
import seki.com.doyouworkout.data.db.mapper.WorkoutMapper
import javax.inject.Inject

class WorkoutRepository @Inject constructor(val db: AppDataBase, val mapper: WorkoutMapper, val cache: DataCache)