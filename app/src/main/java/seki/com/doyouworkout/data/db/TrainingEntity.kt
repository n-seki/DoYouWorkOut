package seki.com.doyouworkout.data.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.StringRes

@Entity(tableName = "training")
data class TrainingEntity(
        @PrimaryKey val id: Int,
        @ColumnInfo(name = "training_name_id") @StringRes val trainingNameId: Int = 0,
        val used: Boolean = true,
        val custom: Boolean = false,
        val customName: String = "",
        val delete: Boolean = false
)