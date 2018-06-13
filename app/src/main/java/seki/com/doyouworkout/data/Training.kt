package seki.com.doyouworkout.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.ColumnInfo.INTEGER
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.StringRes

@Entity(tableName = "training")
data class Training(
        @PrimaryKey @ColumnInfo(typeAffinity = INTEGER) val id: Int,
        @ColumnInfo(name = "training_name_id", typeAffinity = INTEGER) @StringRes val trainingNameId: Int,
        @ColumnInfo(name = "used", typeAffinity = INTEGER) val isUsed: Int
)