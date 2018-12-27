package seki.com.doyouworkout.usecase

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito.*
import seki.com.doyouworkout.data.resource.DateSupplier
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

    private val mockRepository = mock<Repository>()
    private val today = SimpleDateFormat("yyyyMMdd", Locale.US).parse("20181203")
    private val mockDateSupplier = mock<DateSupplier> {
        on { getToday() }.doReturn(today)
    }
    private val sut =
            GetOneDayWorkoutListUseCaseImp(mockRepository, WorkoutMapper(), mockDateSupplier)

    @Test
    fun `トレーニング実績が取得できること`() {
        whenever(mockRepository.getLastWorkout())
                .thenReturn(Maybe.just(WorkoutEntity(today, 1, 1)))

        whenever(mockRepository.getWorkoutList(today, 100))
                .thenReturn(Single.just(
                    listOf(WorkoutEntity(today, 1, 1))
                )
        )

        val trainingEntityList = listOf(
                TrainingEntity(id = 1, name = "腕立て伏せ")
        )

        whenever(mockRepository.getAllTrainingList()).thenReturn(
                Single.just(trainingEntityList)
        )

        val expected = listOf(
                OneDayWorkout(today, listOf(Workout(1, "腕立て伏せ", 1)))
        )

        sut.execute(null)
                .test()
                .await()
                .assertValue(expected)

        verify(mockRepository, times(1)).getAllTrainingList()
    }

    @Test
    fun `トレーニング実績が存在しない場合に空のリストを通知すること`() {
        val expected = listOf<OneDayWorkout>()

        whenever(mockRepository.getWorkoutList(today, 100))
                .thenReturn(Single.just(listOf()))

        val trainingEntityList = listOf(
                TrainingEntity(id = 1, name = "腕立て伏せ")
        )

        whenever(mockRepository.getAllTrainingList())
                .thenReturn(Single.just(trainingEntityList))

        sut.execute(today)
                .test()
                .await()
                .assertValue(expected)

        verify(mockRepository, times(0)).updateWorkout(listOf())
    }

    @Test
    fun `今日までの空トレーニング実績をinsert後に最新のトレーニング実績が取得できること`() {
        whenever(mockRepository.getLastWorkout())
                .thenReturn(Maybe.just(WorkoutEntity(
                        today.previousDay().previousDay().previousDay(), 1, 1)))

        val expected = listOf(
                OneDayWorkout(today, listOf(Workout(1, "腕立て伏せ", 1)))
        )

        whenever(mockRepository.getWorkoutList(today, 100))
                .thenReturn(Single.just(listOf(WorkoutEntity(today, 1, 1))))

        val trainingEntityList = listOf(
                TrainingEntity(id = 1, name = "腕立て伏せ")
        )

        whenever(mockRepository.getAllTrainingList())
                .thenReturn(Single.just(trainingEntityList))

        val emptyWorkout = listOf(
                WorkoutEntity(today.previousDay().previousDay(), 1, 0),
                WorkoutEntity(today.previousDay(), 1, 0))

        whenever(mockRepository.updateWorkout(anyList()))
                .thenReturn(Completable.complete())

        sut.execute(null)
                .test()
                .await()
                .assertValue(expected)

        argumentCaptor<List<WorkoutEntity>>().apply {
            verify(mockRepository).updateWorkout(capture())
            assertThat(firstValue).isEqualTo(emptyWorkout)
        }

    }
}
