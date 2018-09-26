package seki.com.doyouworkout.repository

import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito.*
import seki.com.doyouworkout.data.cache.Cache
import seki.com.doyouworkout.data.db.TrainingEntity
import seki.com.doyouworkout.data.db.WorkoutEntity
import seki.com.doyouworkout.data.repository.LocalRepository
import seki.com.doyouworkout.data.repository.WorkoutRepository
import seki.com.doyouworkout.data.repository.toEntity
import seki.com.doyouworkout.ui.Training
import java.util.*

class WorkoutRepositoryTest {

    @Test
    fun `isInitAppメソッドでLocalRepository#isInitAppが実行されること`() {
        val localRepository = mock(LocalRepository::class.java)
        val cache = mock(Cache::class.java)

        `when`(localRepository.isInitApp()).thenReturn(
                Single.just(true)
        )

        val repository = WorkoutRepository(localRepository, cache)

        repository.isInitApp()
                .test()
                .assertValue(true)

        verify(localRepository).isInitApp()
    }

    @Test
    fun `putDefaultTrainingメソッドでLocalRepositoty#putDefaultTrainingが実行されること`() {
        val localRepository = mock(LocalRepository::class.java)
        val cache = mock(Cache::class.java)

        `when`(localRepository.putDefaultTraining()).thenReturn(
                Completable.complete()
        )

        val repository = WorkoutRepository(localRepository, cache)

        repository.putDefaultTraining()
                .test()
                .assertComplete()

        verify(localRepository).putDefaultTraining()
    }

    @Test
    fun `キャッシュがない状態ではgetAlTrainingListメソッドでLocalRepository#selectTrainingが実行されること`() {
        val localRepository = mock(LocalRepository::class.java)
        val cache = mock(Cache::class.java)

        `when`(cache.hasTraining()).thenReturn(false)

        val repository = WorkoutRepository(localRepository, cache)

        repository.getAllTrainingList()

        verify(localRepository).selectTraining()
        verify(cache, times(0)).getAllTraining()
    }

    @Test
    fun `キャッシュがある状態ではgetAlTrainingListメソッドでCache#getAllTrainingが実行されること`() {
        val localRepository = mock(LocalRepository::class.java)
        val cache = mock(Cache::class.java)

        `when`(cache.hasTraining()).thenReturn(true)

        val repository = WorkoutRepository(localRepository, cache)

        repository.getAllTrainingList()

        verify(localRepository, times(0)).selectTraining()
        verify(cache).getAllTraining()
    }

    @Test
    fun `使用中のTrainingのリストが取得できること`() {
        val localRepository = mock(LocalRepository::class.java)
        val cache = mock(Cache::class.java)

        `when`(cache.hasTraining()).thenReturn(false)
        `when`(localRepository.selectTraining()).thenReturn(
                Single.just(
                        listOf(
                                TrainingEntity(id = 1, name = "one", used = false),
                                TrainingEntity(id = 2, name = "one", used = true)
                        )
                )
        )

        val repository = WorkoutRepository(localRepository, cache)

        repository.getUsedTrainingList()
                .test()
                .assertValues(listOf(TrainingEntity(id = 2, name = "one", used = true)))

    }

    @Test
    fun `putTrainingCacheメソッドでCache#updateTrainingが実行されること`() {
        val localRepository = mock(LocalRepository::class.java)
        val cache = mock(Cache::class.java)

        val repository = WorkoutRepository(localRepository, cache)

        val trainingList = listOf(TrainingEntity(id = 1, name = "test"))

        repository.putTrainingCache(trainingList)

        verify(cache).updateTraining(trainingList)
    }

    @Test
    fun `updateTrainingメソッドでDBとCacheにTraining情報が保存されること`() {
        val localRepository = mock(LocalRepository::class.java)
        val cache = mock(Cache::class.java)
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

        verify(localRepository).insertTraining(trainingEntity)
        verify(cache).updateTraining(trainingEntity)
    }

    @Test
    fun `キャッシュがある場合にgetWorkoutメソッドにてCache#getWorkoutAtが実行されること`() {
        val localRepository = mock(LocalRepository::class.java)
        val cache = mock(Cache::class.java)
        val today = Date()
        `when`(cache.hasWorkoutAt(today)).thenReturn(true)

        val repository = WorkoutRepository(localRepository, cache)

        repository.getWorkout(today)

        verify(cache).getWorkoutAt(today)
        verify(localRepository, times(0)).selectWorkoutAt(today)
    }

    @Test
    fun `updateWorkoutでDBとCacheにWorkout情報が保存されること`() {
        val localRepository = mock(LocalRepository::class.java)
        val cache = mock(Cache::class.java)
        val repository = WorkoutRepository(localRepository, cache)

        val workoutEntity = listOf(
                WorkoutEntity(date = Date(), trainingId = 1, count = 1)
        )

        repository.updateWorkout(workoutEntity)
                .test()
                .assertComplete()

        verify(localRepository).insertWorkout(workoutEntity)
        verify(cache).putWorkout(workoutEntity)
    }
}