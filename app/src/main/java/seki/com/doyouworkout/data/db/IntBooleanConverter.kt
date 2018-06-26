package seki.com.doyouworkout.data.db

import android.arch.persistence.room.TypeConverter

class IntBooleanConverter {

    @TypeConverter
    fun intToBoolean(i: Int) = i != 0

    @TypeConverter
    fun booleanToInt(b: Boolean) = if (b) 1 else 0
}