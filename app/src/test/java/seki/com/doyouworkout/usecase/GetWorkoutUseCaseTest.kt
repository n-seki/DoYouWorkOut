package seki.com.doyouworkout.usecase

import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import seki.com.doyouworkout.data.db.entity.TrainingEntity
import seki.com.doyouworkout.data.db.entity.WorkoutEntity
import seki.com.doyouworkout.data.db.mapper.WorkoutMapper
import seki.com.doyouworkout.data.previousDay
import seki.com.doyouworkout.data.repository.Repository
import seki.com.doyouworkout.ui.Workout
import seki.com.doyouworkout.usecase.impl.GetWorkoutUseCaseImp
import java.text.SimpleDateFormat
import java.util.*

class GetWorkoutUseCaseTest {

    @Test
    fun `Workoutが取得できること`() {

        val repository = mock(Repository::class.java)

        val format = SimpleDateFormat("yyyyMMDD")
        val today = format.parse(format.format(Date()))!!
        val yesterday = today.previousDay()

        val trainingEntityList = listOf(
                TrainingEntity(id = 1, name = "腕立て伏せ")
        )

        `when`(repository.getWorkout(yesterday)).thenReturn(
                Single.create { emitter ->
                    emitter.onSuccess(listOf(WorkoutEntity(yesterday, 1, 1)))
                }
        )

        `when`(repository.getAllTrainingList()).thenReturn(
                Single.create { emitter ->
                    emitter.onSuccess(trainingEntityList)
                }
        )

        val expected = listOf(
                Workout(1, "腕立て伏せ", 1)
        )

        GetWorkoutUseCaseImp(repository, WorkoutMapper())
                .execute(yesterday)
                .test()
                .await()
                .assertValue(expected)
    }
}