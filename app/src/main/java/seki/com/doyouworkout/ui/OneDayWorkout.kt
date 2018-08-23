package seki.com.doyouworkout.ui

import java.text.SimpleDateFormat
import java.util.*

data class OneDayWorkout(
        val trainingDate: Date,
        val workout: List<Workout>
) {

    companion object {
        val format = SimpleDateFormat("yyyy/MM/dd", Locale.US)
    }

    val dateText = format.format(trainingDate)
}