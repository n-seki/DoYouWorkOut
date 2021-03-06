package seki.com.doyouworkout.usecase

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import org.junit.Test
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

        val format = SimpleDateFormat("yyyyMMDD")
        val today = format.parse(format.format(Date()))!!
        val yesterday = today.previousDay()

        val repository = mock<Repository> {
            on { getWorkout(yesterday) }
                    .doReturn(Single.just(listOf(WorkoutEntity(yesterday, 1, 1))))

            on { getAllTrainingList() }
                    .doReturn(Single.just(listOf(TrainingEntity(id = 1, name = "腕立て伏せ"))))
        }

        val expected = listOf(
                Workout(1, "腕立て伏せ", 1)
        )

        GetWorkoutUseCaseImp(repository, WorkoutMapper())
                .execute(yesterday)
                .test()
                .await()
                .assertValue(expected)
    }

    @Test
    fun `日付指定なしの場合に回数0のWorkoutのリストが取得できること`() {
        val trainingEntityList =
                listOf(
                        TrainingEntity(id = 0, name = "腹筋"),
                        TrainingEntity(id = 1, name = "腕立て伏せ"),
                        TrainingEntity(id = 2, name = "背筋"),
                        TrainingEntity(id = 3, name = "スクワット")
                )

        val repository = mock<Repository> {
            on { getUsedTrainingList() }.doReturn(Single.just(trainingEntityList))
        }

        val expected = trainingEntityList.map { Workout(it.id, it.name, 0) }

        GetWorkoutUseCaseImp(repository, WorkoutMapper())
                .execute(null)
                .test()
                .await()
                .assertValue(expected)
    }

}