package seki.com.doyouworkout.ui

import android.os.Parcel
import android.os.Parcelable

data class Workout(
        val id: Int,
        val name: String,
        var count: Int,
        val isUsed: Boolean = true
): Parcelable {

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(count)
        parcel.writeByte(if (isUsed) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Workout> {
        override fun createFromParcel(parcel: Parcel): Workout {
            return Workout(
                    parcel.readInt(),
                    parcel.readString(),
                    parcel.readInt(),
                    parcel.readByte().toInt() != 0)
        }

        override fun newArray(size: Int): Array<Workout?> {
            return arrayOfNulls(size)
        }
    }
}