package seki.com.doyouworkout.data.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import java.util.*

@Entity(tableName = "workout",
        primaryKeys = ["date", "training_id"],
        foreignKeys =
        [ForeignKey(entity = TrainingEntity::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("training_id"))
        ],
        indices = [(Index("training_id"))])
data class WorkoutEntity(
        val date: Date,
        @ColumnInfo(name = "training_id") val trainingId: Int,
        val count: Int
)