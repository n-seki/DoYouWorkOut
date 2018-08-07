package seki.com.doyouworkout.data.cache

import io.reactivex.Single
import org.hamcrest.CoreMatchers.*
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Test
import seki.com.doyouworkout.data.db.TrainingEntity
import seki.com.doyouworkout.data.db.WorkoutEntity
import java.util.*

class DataCacheTest {

    private val dataCache = DataCache()

    @After
    fun clear() {
        dataCache.clear()
    }

    @Test
    fun `Workoutを追加していないときにhasWorkoutAtがfalseであること`() {
        assertThat(dataCache.hasWorkoutAt(Date()), `is`(false))
    }

    @Test
    fun `Workoutを追加したときに該当日のhasWorkoutAtがtrueであること`() {
        val date = Date()
        val workoutEntity = listOf(WorkoutEntity(date = date, trainingId = 1, count = 1))
        dataCache.putWorkout(workoutEntity)

        assertThat(dataCache.hasWorkoutAt(date), `is`(true))
    }

    @Test
    fun `Trainingを追加していないときにhasTrainingがfalseであること`() {
        assertThat(dataCache.hasTraining(), `is`(false))
    }

    @Test
    fun `Trainingを追加したときにhasTrainingがtrueであること`() {
        val trainingEntity = listOf(TrainingEntity(id = 1))
        dataCache.putTraining(trainingEntity)

        assertThat(dataCache.hasTraining(), `is`(true))
    }

    @Test
    fun `Workoutを追加していない場合にgetWorkoutAtがnullを通知すること`() {
        assertThat(dataCache.getWorkoutAt(Date()), `is`(nullValue()))
    }

    @Test
    fun `Workoutを追加したときに該当日のgetWorkoutAtでWorkoutリストが通知されること`() {
        val date = Date()
        val workoutEntity = listOf(WorkoutEntity(date = date, trainingId = 1, count = 1))
        dataCache.putWorkout(workoutEntity)

        assertThat(dataCache.getWorkoutAt(date), `is`(notNullValue()))
        assertThat(dataCache.getWorkoutAt(date), `is`(Single.just(workoutEntity)))
    }

    @Test
    fun `Trainingを追加していない場合にgetTrainingがnullを通知すること`() {
        assertThat(dataCache.getTraining(1), `is`(nullValue()))
    }

    @Test
    fun `Trainingを追加したときにgetTrainingでTrainingが通知されること`() {
        val trainingEntity = listOf(TrainingEntity(id = 1))
        dataCache.putTraining(trainingEntity)

        assertThat(dataCache.getTraining(1), `is`(notNullValue()))
        assertThat(dataCache.getTraining(1), `is`(trainingEntity[0]))
    }
}