package seki.com.doyouworkout.repository

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import seki.com.doyouworkout.data.cache.Cache
import seki.com.doyouworkout.data.db.entity.TrainingEntity
import seki.com.doyouworkout.data.db.entity.WorkoutEntity
import seki.com.doyouworkout.data.repository.LocalRepository
import seki.com.doyouworkout.data.repository.WorkoutRepository
import seki.com.doyouworkout.data.repository.toEntity
import seki.com.doyouworkout.ui.Training
import java.util.*

class WorkoutRepositoryTest {

    @Test
    fun `isInitAppメソッドでLocalRepository#isInitAppが実行されること`() {
        val localRepository = mock<LocalRepository> {
            on { isInitApp() }.doReturn(Single.just(true))
        }

        val repository = WorkoutRepository(localRepository, mock())

        repository.isInitApp()
                .test()
                .assertValue(true)

        verify(localRepository).isInitApp()
    }

    @Test
    fun `putDefaultTrainingメソッドでLocalRepository#putDefaultTrainingが実行されること`() {
        val localRepository = mock<LocalRepository> {
            on { putDefaultTraining() }.doReturn(Completable.complete())
        }

        val repository = WorkoutRepository(localRepository, mock())

        repository.putDefaultTraining()
                .test()
                .assertComplete()

        verify(localRepository).putDefaultTraining()
    }

    @Test
    fun `キャッシュがない状態ではgetAlTrainingListメソッドでLocalRepository#selectTrainingが実行されること`() {
        val localRepository = mock<LocalRepository>()
        val cache = mock<Cache> {
            on { hasTraining() }.doReturn(false)
        }

        val repository = WorkoutRepository(localRepository, cache)

        repository.getAllTrainingList()

        verify(localRepository).selectTraining()
        verify(cache, times(0)).getAllTraining()
    }

    @Test
    fun `キャッシュがある状態ではgetAlTrainingListメソッドでCache#getAllTrainingが実行されること`() {
        val localRepository = mock<LocalRepository>()
        val cache = mock<Cache> {
            on { hasTraining() }.doReturn(true)
        }

        val repository = WorkoutRepository(localRepository, cache)

        repository.getAllTrainingList()

        verify(localRepository, times(0)).selectTraining()
        verify(cache).getAllTraining()
    }

    @Test
    fun `使用中のTrainingのリストが取得できること`() {
        val localRepository = mock<LocalRepository> {
            on { selectTraining() }.doReturn(
                    Single.just(
                            listOf(TrainingEntity(id = 1, name = "one", used = false),
                                    TrainingEntity(id = 2, name = "one", used = true)))
            )
        }
        val cache = mock<Cache> {
            on { hasTraining() }.doReturn(false)
        }

        val repository = WorkoutRepository(localRepository, cache)

        repository.getUsedTrainingList()
                .test()
                .assertValues(listOf(TrainingEntity(id = 2, name = "one", used = true)))

    }

    @Test
    fun `putTrainingCacheメソッドでCache#updateTrainingが実行されること`() {
        val cache = mock<Cache>()
        val repository = WorkoutRepository(mock(), cache)

        val trainingList = listOf(TrainingEntity(id = 1, name = "test"))

        repository.putTrainingCache(trainingList)

        argumentCaptor<List<TrainingEntity>>().apply {
            verify(cache).updateTraining(capture())
            assertEquals(firstValue, trainingList)
        }

    }

    @Test
    fun `updateTrainingメソッドでDBとCacheにTraining情報が保存されること`() {
        val localRepository = mock<LocalRepository>()
        val cache = mock<Cache>()
        val repository = WorkoutRepository(localRepository, cache)

        val training = listOf(
                Training(
                        id = 1,
                        name = "test",
                        isUsed = true,
                        isCustom = false,
                        isDeleted = false)
        )

        val trainingEntity = training.map { it.toEntity() }

        repository.updateTraining(training)
                .test()
                .assertComplete()

        argumentCaptor<List<TrainingEntity>>().apply {
            verify(localRepository).insertTraining(capture())
            assertEquals(firstValue, trainingEntity)
        }

        argumentCaptor<List<TrainingEntity>>().apply {
            verify(cache).updateTraining(capture())
            assertEquals(firstValue, trainingEntity)
        }

    }

    @Test
    fun `キャッシュがある場合にgetWorkoutメソッドにてCache#getWorkoutAtが実行されること`() {
        val today = Date()
        val localRepository = mock<LocalRepository>()
        val cache = mock<Cache> {
            on { hasWorkoutAt(today) }.doReturn(true)
        }

        val repository = WorkoutRepository(localRepository, cache)

        repository.getWorkout(today)

        argumentCaptor<Date>().apply {
            verify(cache).getWorkoutAt(capture())
            assertEquals(firstValue, today)
        }

        verify(localRepository, times(0)).selectWorkoutAt(any())
    }

    @Test
    fun `updateWorkoutでDBとCacheにWorkout情報が保存されること`() {
        val localRepository = mock<LocalRepository>()
        val cache = mock<Cache>()
        val repository = WorkoutRepository(localRepository, cache)

        val workoutEntity = listOf(
                WorkoutEntity(date = Date(), trainingId = 1, count = 1)
        )

        repository.updateWorkout(workoutEntity)
                .test()
                .assertComplete()

        argumentCaptor<List<WorkoutEntity>>().apply {
            verify(localRepository).insertWorkout(capture())
            assertEquals(firstValue, workoutEntity)
        }

        argumentCaptor<List<WorkoutEntity>>().apply {
            verify(cache).putWorkout(capture())
            assertEquals(firstValue, workoutEntity)
        }
    }
}