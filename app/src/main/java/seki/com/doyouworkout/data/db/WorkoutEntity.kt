package seki.com.doyouworkout.data.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import java.util.*

@Entity(tableName = "workout", primaryKeys = ["date", "training_id"])
data class WorkoutEntity(
        val date: Date,
        @ColumnInfo(name = "training_id") val trainingId: Int,
        val count: Int
)