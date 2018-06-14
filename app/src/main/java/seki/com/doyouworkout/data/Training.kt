package seki.com.doyouworkout.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.StringRes

@Entity(tableName = "training")
data class Training(
        @PrimaryKey val id: Int,
        @ColumnInfo(name = "training_name_id") @StringRes val trainingNameId: Int,
        @ColumnInfo(name = "used") val isUsed: Int,
        @ColumnInfo(name = "custom") val isCustom: Int,
        val customName: String,
        @ColumnInfo(name = "delete") val isDeleted: Int
)