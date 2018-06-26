package seki.com.doyouworkout.domain

import seki.com.doyouworkout.data.db.mapper.WorkoutMapper
import seki.com.doyouworkout.data.repository.WorkoutRepository
import seki.com.doyouworkout.ui.Training
import javax.inject.Inject

class TrainingDomain @Inject constructor(
        private val repository: WorkoutRepository, private val workoutMapper: WorkoutMapper) {

    fun getAllTraining(): List<Training> {
        return repository
                .getDummyTraining()
                .map { workoutMapper.toTraining(it) }
    }

    fun updateTraining(list: List<Training>) {
         repository.updateTraining(list)
    }
}