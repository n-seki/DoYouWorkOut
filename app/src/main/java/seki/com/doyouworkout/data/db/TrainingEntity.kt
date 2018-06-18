package seki.com.doyouworkout.data.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.StringRes

@Entity(tableName = "training")
data class TrainingEntity(
        @PrimaryKey val id: Int,
        @ColumnInfo(name = "training_name_id") @StringRes val trainingNameId: Int,
        private val used: Int,
        private val custom: Int = 0,
        val customName: String = "",
        private val delete: Int = 0
) {
    val isUsed: Boolean get() = used != 0
    val isCustom: Boolean get() = custom != 0
    val isDeleted: Boolean get() = delete !=0
}