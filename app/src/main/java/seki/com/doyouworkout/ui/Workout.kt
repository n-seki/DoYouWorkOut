package seki.com.doyouworkout.ui

import java.io.Serializable

data class Workout(
        val id: Int,
        val name: String,
        var count: Int,
        val isUsed: Boolean = true
): Serializable