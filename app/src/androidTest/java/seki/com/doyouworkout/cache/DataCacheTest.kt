package seki.com.doyouworkout.cache

import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Test
import seki.com.doyouworkout.data.cache.CacheImp
import seki.com.doyouworkout.data.db.entity.TrainingEntity
import seki.com.doyouworkout.data.db.entity.WorkoutEntity
import java.util.*

class DataCacheTest {

    private val dataCache = CacheImp()

    @After
    fun clear() {
        dataCache.clear()
    }

    @Test
    fun `Workoutを追加していないときにhasWorkoutAtがfalseであること`() {
        assertThat(dataCache.hasWorkoutAt(Date())).isFalse()
    }

    @Test
    fun `Workoutを追加したときに該当日のhasWorkoutAtがtrueであること`() {
        val date = Date()
        val workoutEntity = listOf(WorkoutEntity(date = date, trainingId = 1, count = 1))
        dataCache.putWorkout(workoutEntity)

        assertThat(dataCache.hasWorkoutAt(date)).isTrue()
    }

    @Test
    fun `Trainingを追加していないときにhasTrainingがfalseであること`() {
        assertThat(dataCache.hasTraining()).isFalse()
    }

    @Test
    fun `Trainingを追加したときにhasTrainingがtrueであること`() {
        val trainingEntity = listOf(TrainingEntity(id = 1, name = "test"))
        dataCache.putTraining(trainingEntity)

        assertThat(dataCache.hasTraining()).isTrue()
    }

    @Test(expected = NullPointerException::class)
    fun `Workoutを追加していない場合にgetWorkoutAtが例外をthrowすること`() {
        dataCache.getWorkoutAt(Date())
                .test()
                .assertValue { list -> list.isEmpty() }
    }

    @Test
    fun `Workoutを追加したときに該当日のgetWorkoutAtでWorkoutリストが通知されること`() {
        val date = Date()
        val workoutEntity = listOf(WorkoutEntity(date = date, trainingId = 1, count = 1))
        dataCache.putWorkout(workoutEntity)

        dataCache.getWorkoutAt(date)
                .test()
                .assertValue(workoutEntity)
    }

    @Test
    fun `Trainingを追加していない場合にgetTrainingがnullを通知すること`() {
        assertThat(dataCache.getTraining(1)).isNull()
    }

    @Test
    fun `Trainingを追加したときにgetTrainingでTrainingが通知されること`() {
        val trainingEntity = listOf(TrainingEntity(id = 1, name = "test"))
        dataCache.putTraining(trainingEntity)

        assertThat(dataCache.getTraining(1)).isEqualTo(trainingEntity[0])
    }
}