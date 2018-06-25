package seki.com.doyouworkout.ui

data class Workout(
        val id: Int,
        val name: String,
        val count: Int,
        val isUsed: Boolean = true
)