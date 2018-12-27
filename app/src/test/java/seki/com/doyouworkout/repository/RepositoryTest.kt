package seki.com.doyouworkout.repository

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import seki.com.doyouworkout.data.cache.Cache
import seki.com.doyouworkout.data.db.entity.TrainingEntity
import seki.com.doyouworkout.data.db.entity.WorkoutEntity
import seki.com.doyouworkout.data.repository.Repository
import seki.com.doyouworkout.data.repository.impl.RepositoryImp
import seki.com.doyouworkout.data.repository.impl.toEntity
import seki.com.doyouworkout.ui.Training
import java.util.*

class RepositoryTest {

    @Test
    fun `isInitAppメソッドでLocalRepository#isInitAppが実行されること`() {
        val localRepository = mock<Repository> {
            on { isInitApp() }.doReturn(Single.just(true))
        }

        val repository = RepositoryImp(localRepository, mock())

        repository.isInitApp()
                .test()
                .assertValue(true)

        verify(localRepository).isInitApp()
    }

    @Test
    fun `putDefaultTrainingメソッドでLocalRepository#putDefaultTrainingが実行されること`() {
        val localRepository = mock<Repository> {
            on { putDefaultTraining() }.doReturn(Completable.complete())
        }

        val repository = RepositoryImp(localRepository, mock())

        repository.putDefaultTraining()
                .test()
                .assertComplete()

        verify(localRepository).putDefaultTraining()
    }

    @Test
    fun `キャッシュがない状態ではgetAlTrainingListメソッドでLocalRepository#selectTrainingが実行されること`() {
        val localRepository = mock<Repository>()
        val cache = mock<Cache> {
            on { hasTraining() }.doReturn(false)
        }

        val repository = RepositoryImp(localRepository, cache)

        repository.getAllTrainingList()

        verify(localRepository).getAllTrainingList()
        verify(cache, times(0)).getAllTraining()
    }

    @Test
    fun `キャッシュがある状態ではgetAlTrainingListメソッドでCache#getAllTrainingが実行されること`() {
        val localRepository = mock<Repository>()
        val cache = mock<Cache> {
            on { hasTraining() }.doReturn(true)
        }

        val repository = RepositoryImp(localRepository, cache)

        repository.getAllTrainingList()

        verify(localRepository, times(0)).getAllTrainingList()
        verify(cache).getAllTraining()
    }

    @Test
    fun `使用中のTrainingのリストが取得できること`() {
        val localRepository = mock<Repository> {
            on { getAllTrainingList() }.doReturn(
                    Single.just(
                            listOf(TrainingEntity(id = 1, name = "one", used = false),
                                    TrainingEntity(id = 2, name = "one", used = true)))
            )
        }
        val cache = mock<Cache> {
            on { hasTraining() }.doReturn(false)
        }

        val repository = RepositoryImp(localRepository, cache)

        repository.getUsedTrainingList()
                .test()
                .assertValues(listOf(TrainingEntity(id = 2, name = "one", used = true)))

    }

    @Test
    fun `putTrainingCacheメソッドでCache#updateTrainingが実行されること`() {
        val cache = mock<Cache>()
        val repository = RepositoryImp(mock(), cache)

        val trainingList = listOf(TrainingEntity(id = 1, name = "test"))

        repository.putTrainingCache(trainingList)

        argumentCaptor<List<TrainingEntity>>().apply {
            verify(cache).updateTraining(capture())
            assertThat(firstValue).isEqualTo(trainingList)
        }

    }

    @Test
    fun `updateTrainingメソッドでDBとCacheにTraining情報が保存されること`() {

        val training = listOf(
                Training(
                        id = 1,
                        name = "test",
                        isUsed = true,
                        isCustom = false,
                        isDeleted = false)
        )

        val localRepository = mock<Repository> {
            on { updateTraining(training) }
                    .thenReturn(Completable.complete())
        }
        val cache = mock<Cache>()
        val repository = RepositoryImp(localRepository, cache)

        val trainingEntity = training.map { it.toEntity() }

        repository.updateTraining(training)
                .test()
                .assertComplete()

        argumentCaptor<List<Training>>().apply {
            verify(localRepository).updateTraining(capture())
            assertThat(firstValue).isEqualTo(training)
        }

        argumentCaptor<List<TrainingEntity>>().apply {
            verify(cache).updateTraining(capture())
            assertThat(firstValue).isEqualTo(trainingEntity)
        }

    }

    @Test
    fun `キャッシュがある場合にgetWorkoutメソッドにてCache#getWorkoutAtが実行されること`() {
        val today = Date()
        val localRepository = mock<Repository>()
        val cache = mock<Cache> {
            on { hasWorkoutAt(today) }.doReturn(true)
        }

        val repository = RepositoryImp(localRepository, cache)

        repository.getWorkout(today)

        argumentCaptor<Date>().apply {
            verify(cache).getWorkoutAt(capture())
            assertThat(firstValue).isEqualTo(today)
        }

        verify(localRepository, times(0)).getWorkout(any())
    }

    @Test
    fun `updateWorkoutでDBとCacheにWorkout情報が保存されること`() {
        val workoutEntity = listOf(
                WorkoutEntity(date = Date(), trainingId = 1, count = 1)
        )

        val localRepository = mock<Repository> {
            on { updateWorkout(workoutEntity) }
                    .thenReturn(Completable.complete())
        }

        val cache = mock<Cache>()
        val repository = RepositoryImp(localRepository, cache)

        repository.updateWorkout(workoutEntity)
                .test()
                .assertComplete()

        argumentCaptor<List<WorkoutEntity>>().apply {
            verify(localRepository).updateWorkout(capture())
            assertThat(firstValue).isEqualTo(workoutEntity)
        }

        argumentCaptor<List<WorkoutEntity>>().apply {
            verify(cache).putWorkout(capture())
            assertThat(firstValue).isEqualTo(workoutEntity)
        }
    }
}