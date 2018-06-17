package seki.com.doyouworkout.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "workout")
data class WorkoutEntity(
        @PrimaryKey val date: Date,
        @PrimaryKey @ColumnInfo(name = "training_id") val trainingId: Int,
        val count: Int
)