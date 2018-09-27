package seki.com.doyouworkout.usecase

import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito.*
import seki.com.doyouworkout.data.db.entity.TrainingEntity
import seki.com.doyouworkout.data.db.entity.WorkoutEntity
import seki.com.doyouworkout.data.db.mapper.WorkoutMapper
import seki.com.doyouworkout.data.previousDay
import seki.com.doyouworkout.data.repository.Repository
import seki.com.doyouworkout.ui.OneDayWorkout
import seki.com.doyouworkout.ui.Workout
import java.text.SimpleDateFormat
import java.util.*

class WorkoutUseCaseTest {

    private val mockRepository = mock(Repository::class.java)
    private val sut = WorkoutUseCase(mockRepository, WorkoutMapper(), TestSchedulersProvider)

    @Test
    fun `Workoutが取得できること`() {
        val format = SimpleDateFormat("yyyyMMDD")
        val today = format.parse(format.format(Date()))!!
        val yesterday = today.previousDay()

        val trainingEntityList = listOf(
                TrainingEntity(id = 1, name = "腕立て伏せ")
        )

        `when`(mockRepository.getWorkout(yesterday)).thenReturn(
                Single.create { emitter -> emitter.onSuccess(
                        listOf(WorkoutEntity(yesterday, 1, 1))
                ) }
        )

        `when`(mockRepository.getAllTrainingList()).thenReturn(
                Single.create { emitter -> emitter.onSuccess(trainingEntityList) }
        )

        val expected = listOf(
                Workout(1, "腕立て伏せ", 1)
        )

        sut.getWorkout(yesterday)
                .test()
                .await()
                .assertValue(expected)
    }

    @Test
    fun `回数0のWorkoutのリストが取得できること`() {
        val trainingEntityList =
                listOf(
                        TrainingEntity(id = 0, name = "腹筋"),
                        TrainingEntity(id = 1, name = "腕立て伏せ"),
                        TrainingEntity(id = 2, name = "背筋"),
                        TrainingEntity(id = 3, name = "スクワット")
                        )

        `when`(mockRepository.getUsedTrainingList()).thenReturn(
                Single.create { emitter -> emitter.onSuccess(
                        trainingEntityList
                ) }
        )

        val expected = trainingEntityList.map { Workout(it.id, it.name, 0) }

        sut.fetchEmptyWorkout()
                .test()
                .await()
                .assertValue(expected)
    }

    @Test
    fun `トレーニング実績が取得できること`() {
        val format = SimpleDateFormat("yyyyMMDD")
        val today = format.parse(format.format(Date().previousDay()))!!

        `when`(mockRepository.getWorkoutList(100))
                .thenReturn(Single.create {
                    emitter -> emitter.onSuccess(listOf(WorkoutEntity(today, 1, 1))
                ) }
        )

        val trainingEntityList = listOf(
                TrainingEntity(id = 1, name = "腕立て伏せ")
        )

        `when`(mockRepository.getAllTrainingList()).thenReturn(
                Single.create { emitter -> emitter.onSuccess(
                        trainingEntityList
                ) }
        )

        val expected = listOf(
                OneDayWorkout(today, listOf(Workout(1, "腕立て伏せ", 1)))
        )

        sut.fetchOneDayWorkoutList()
                .test()
                .await()
                .assertValue(expected)

        verify(mockRepository).getAllTrainingList()
    }

    @Test
    fun `トレーニング実績が存在しない場合に空のリストを通知すること`() {
        val expected = listOf<OneDayWorkout>()

        `when`(mockRepository.getWorkoutList(1))
                .thenReturn(Single.create {
                    emitter -> emitter.onSuccess(listOf())
                })

        `when`(mockRepository.getWorkoutList(100))
                .thenReturn(Single.create {
                    emitter -> emitter.onSuccess(listOf())
                })

        val trainingEntityList = listOf(
                TrainingEntity(id = 1, name = "腕立て伏せ")
        )

        `when`(mockRepository.getAllTrainingList())
                .thenReturn(Single.just(trainingEntityList))


        sut.fetchAndInsertOneDayWorkout()
                .test()
                .await()
                .assertValue(expected)

        verify(mockRepository, times(0)).updateWorkout(listOf())

    }

    @Test
    fun `今日までの空トレーニング実績をinsert後に最新のトレーニング実績が取得できること`() {
        val format = SimpleDateFormat("yyyyMMDD")
        val today = format.parse(format.format(Date()))!!

        val expected = listOf(
                OneDayWorkout(today, listOf(Workout(1, "腕立て伏せ", 1)))
        )

        `when`(mockRepository.getWorkoutList(1))
                .thenReturn(Single.create {
                    emitter -> emitter.onSuccess(listOf(
                        WorkoutEntity(today.previousDay().previousDay(), 1, 1)
                )) })

        `when`(mockRepository.getWorkoutList(100))
                .thenReturn(Single.create {
                    emitter -> emitter.onSuccess(listOf(WorkoutEntity(today, 1, 1)))
                })

        val trainingEntityList = listOf(
                TrainingEntity(id = 1, name = "腕立て伏せ")
        )

        `when`(mockRepository.getAllTrainingList())
                .thenReturn(Single.just(trainingEntityList))

        val emptyWorkout = listOf(
                WorkoutEntity(today.previousDay().previousDay(), 1, 0),
                WorkoutEntity(today.previousDay(), 1, 0)
        )

        `when`(mockRepository.updateWorkout(emptyWorkout))
                .thenReturn(Completable.complete())

        sut.fetchAndInsertOneDayWorkout()
                .test()
                .await()
                .assertValue(expected)

        verify(mockRepository).updateWorkout(emptyWorkout)
    }

    @Test
    fun `空のWorkoutListが作成されること`() {
        val format = SimpleDateFormat("yyyyMMDD")
        val yesterday = format.parse(format.format(Date().previousDay()))!!

        val trainingEntityList = listOf(
                TrainingEntity(id = 1, name = "腕立て伏せ")
        )

        sut.createEmptyWorkoutList(yesterday, trainingEntityList)
                .test()
                .await()
                .assertValue(
                        listOf(WorkoutEntity(yesterday, 1, 0))
                )
    }


}
