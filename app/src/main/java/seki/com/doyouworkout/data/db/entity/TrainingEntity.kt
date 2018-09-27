package seki.com.doyouworkout.data.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "training")
data class TrainingEntity(
        @PrimaryKey val id: Int,
        val used: Boolean = true,
        val custom: Boolean = false,
        val name: String,
        val delete: Boolean = false
)