package seki.com.doyouworkout.usecase

import org.junit.Test
import org.mockito.Mockito.mock
import seki.com.doyouworkout.data.cache.Cache
import seki.com.doyouworkout.data.db.mapper.WorkoutMapper
import seki.com.doyouworkout.data.repository.MockRepository
import seki.com.doyouworkout.data.repository.previousDay
import seki.com.doyouworkout.ui.OneDayWorkout
import seki.com.doyouworkout.ui.Workout
import java.text.SimpleDateFormat
import java.util.*

class WorkoutUseCaseTest {

    private val mockCache = mock(Cache::class.java)
    private val mockRepository = MockRepository(mockCache)
    private val sut = WorkoutUseCase(mockRepository, WorkoutMapper(), TestSchedulersProvider)

    @Test
    fun `Workoutが取得できること`() {
        val expected = listOf(
                Workout(1, "腕立て伏せ", 1)
        )

        sut.getWorkout(Date())
                .test()
                .await()
                .assertValue(expected)
    }

    @Test
    fun `回数0のWorkoutのリストが取得できること`() {
        val expected = MockRepository.defaultTraining.map {
            Workout(it.id, it.name, 0)
        }

        sut.fetchEmptyWorkout()
                .test()
                .await()
                .assertValue(expected)
    }

    @Test
    fun `トレーニング実績が取得できること`() {
        val format = SimpleDateFormat("yyyyMMDD")
        val today = format.parse("20180624")
        val expected = listOf(
                OneDayWorkout(today.previousDay(), listOf(Workout(1, "腕立て伏せ", 1)))
        )

        sut.fetchOneDayWorkoutList()
                .test()
                .await()
                .assertValue(expected)
    }
}
