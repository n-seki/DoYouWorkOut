package seki.com.doyouworkout.ui

import java.util.*

data class OneDayWorkout(
        val trainingDate: Date,
        val workout: List<Workout>
)