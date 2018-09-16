package seki.com.doyouworkout.usecase

import org.junit.Test
import org.mockito.Mockito.mock
import seki.com.doyouworkout.data.cache.Cache
import seki.com.doyouworkout.data.db.mapper.WorkoutMapper
import seki.com.doyouworkout.data.repository.MockRepository
import seki.com.doyouworkout.ui.Workout
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
}
