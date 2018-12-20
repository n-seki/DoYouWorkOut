package seki.com.doyouworkout.usecase

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Completable
import org.junit.Test
import seki.com.doyouworkout.data.db.entity.WorkoutEntity
import seki.com.doyouworkout.data.repository.Repository
import seki.com.doyouworkout.ui.Workout
import seki.com.doyouworkout.usecase.impl.UpdateWorkoutUseCaseImp
import java.text.SimpleDateFormat
import java.util.*

class UpdateWorkoutUseCaseTest {

    @Test
    fun `更新成功でtrueを通知すること`() {

        val format = SimpleDateFormat("yyyyMMDD")
        val today = format.parse(format.format(Date()))!!

        val training = listOf(
                WorkoutEntity(date = today, trainingId = 1, count = 1)
        )

        val repository = mock<Repository> {
            on { updateWorkout(training) }.doReturn(Completable.complete())
        }

        val workout = listOf(
                Workout(id = 1, name = "腹筋", count = 1)
        )

        UpdateWorkoutUseCaseImp(repository)
                .execute(today, workout)
                .test()
                .await()
                .assertValue(true)
    }

    @Test
    fun `更新失敗でfalseを通知すること`() {

        val format = SimpleDateFormat("yyyyMMDD")
        val today = format.parse(format.format(Date()))!!

        val training = listOf(
                WorkoutEntity(date = today, trainingId = 1, count = 1)
        )

        val repository = mock<Repository> {
            on { updateWorkout(training) }.doReturn(Completable.error(RuntimeException()))
        }

        val workout = listOf(
                Workout(id = 1, name = "腹筋", count = 1)
        )

        UpdateWorkoutUseCaseImp(repository)
                .execute(today, workout)
                .test()
                .await()
                .assertValue(false)
    }
}