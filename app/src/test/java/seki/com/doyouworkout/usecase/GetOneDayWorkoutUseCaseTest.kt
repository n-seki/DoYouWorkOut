package seki.com.doyouworkout.usecase

import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito.*
import seki.com.doyouworkout.data.DateSupplier
import seki.com.doyouworkout.data.db.entity.TrainingEntity
import seki.com.doyouworkout.data.db.entity.WorkoutEntity
import seki.com.doyouworkout.data.db.mapper.WorkoutMapper
import seki.com.doyouworkout.data.previousDay
import seki.com.doyouworkout.data.repository.Repository
import seki.com.doyouworkout.ui.OneDayWorkout
import seki.com.doyouworkout.ui.Workout
import seki.com.doyouworkout.usecase.impl.GetOneDayWorkoutListUseCaseImp
import java.text.SimpleDateFormat
import java.util.*

class GetOneDayWorkoutUseCaseTest {

    private val mockRepository = mock(Repository::class.java)
    private val today = SimpleDateFormat("yyyyMMdd", Locale.US).parse("20181203")
    private val mockDateSupplier = mock(DateSupplier::class.java).apply {
        `when`(this.getToday()).thenReturn(today)
    }
    private val sut =
            GetOneDayWorkoutListUseCaseImp(mockRepository, WorkoutMapper(), mockDateSupplier)

    @Test
    fun `トレーニング実績が取得できること`() {
        `when`(mockRepository.getLastWorkout())
                .thenReturn(Single.just(WorkoutEntity(today, 1, 1)))

        `when`(mockRepository.getWorkoutList(today, 100))
                .thenReturn(Single.just(
                    listOf(WorkoutEntity(today, 1, 1))
                )
        )

        val trainingEntityList = listOf(
                TrainingEntity(id = 1, name = "腕立て伏せ")
        )

        `when`(mockRepository.getAllTrainingList()).thenReturn(
                Single.just(trainingEntityList)
        )

        val expected = listOf(
                OneDayWorkout(today, listOf(Workout(1, "腕立て伏せ", 1)))
        )

        sut.execute(null)
                .test()
                .await()
                .assertValue(expected)

        verify(mockRepository).getAllTrainingList()
    }

    @Test
    fun `トレーニング実績が存在しない場合に空のリストを通知すること`() {
        val expected = listOf<OneDayWorkout>()

        `when`(mockRepository.getWorkoutList(today, 100))
                .thenReturn(Single.just(listOf()))

        val trainingEntityList = listOf(
                TrainingEntity(id = 1, name = "腕立て伏せ")
        )

        `when`(mockRepository.getAllTrainingList())
                .thenReturn(Single.just(trainingEntityList))


        sut.execute(today)
                .test()
                .await()
                .assertValue(expected)

        verify(mockRepository, times(0)).updateWorkout(listOf())

    }

    @Test
    fun `今日までの空トレーニング実績をinsert後に最新のトレーニング実績が取得できること`() {
        `when`(mockRepository.getLastWorkout())
                .thenReturn(Single.just(WorkoutEntity(
                        today.previousDay().previousDay().previousDay(), 1, 1)))

        val expected = listOf(
                OneDayWorkout(today, listOf(Workout(1, "腕立て伏せ", 1)))
        )

        `when`(mockRepository.getWorkoutList(today, 100))
                .thenReturn(Single.just(listOf(WorkoutEntity(today, 1, 1))))

        val trainingEntityList = listOf(
                TrainingEntity(id = 1, name = "腕立て伏せ")
        )

        `when`(mockRepository.getAllTrainingList())
                .thenReturn(Single.just(trainingEntityList))

        val emptyWorkout = listOf(
                WorkoutEntity(today.previousDay().previousDay(), 1, 0),
                WorkoutEntity(today.previousDay(), 1, 0))

        `when`(mockRepository.updateWorkout(anyList()))
                .thenReturn(Completable.complete())

        sut.execute(null)
                .test()
                .await()
                .assertValue(expected)

        verify(mockRepository).updateWorkout(emptyWorkout)
    }
}
