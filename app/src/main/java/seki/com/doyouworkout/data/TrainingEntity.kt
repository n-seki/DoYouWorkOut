package seki.com.doyouworkout.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.StringRes

@Entity(tableName = "training")
data class TrainingEntity(
        @PrimaryKey val id: Int,
        @ColumnInfo(name = "training_name_id") @StringRes val trainingNameId: Int,
        private val used: Int,
        private val custom: Int,
        val customName: String,
        private val delete: Int
) {
    val isUsed: Boolean get() = used != 0
    val isCustom: Boolean get() = custom != 0
    val isDeleted: Boolean get() = delete !=0
}