package seki.com.doyouworkout.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.ColumnInfo.INTEGER
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "workout")
data class Workout(
        @PrimaryKey val date: String,
        @PrimaryKey @ColumnInfo(name = "training_id", typeAffinity = INTEGER) val trainingId: Int,
        @ColumnInfo(typeAffinity = INTEGER) val count: Int
)