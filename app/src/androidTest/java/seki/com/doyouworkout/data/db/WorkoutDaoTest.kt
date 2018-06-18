package seki.com.doyouworkout.data.db

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.filters.SmallTest
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.empty
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class) @SmallTest
class WorkoutDaoTest {

    lateinit var db: AppDataBase
    lateinit var dao: WorkoutDao

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java).build()
        dao = db.workoutDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun `workoutがインサート後にロードできることのテスト`() {
        val workout = WorkoutEntity(Date(), 1, 1)
        dao.insert(workout)

        val actual: List<WorkoutEntity> = dao.load()
        assertThat(actual[0].trainingId, `is`(workout.trainingId))
    }

    @Test
    fun `workoutをインサート後にdeleteできることのテスト`() {
        val workout = WorkoutEntity(Date(), 1, 1)
        dao.insert(workout)

        dao.delete(workout)

        val actual: List<WorkoutEntity> = dao.load()
        assertThat(actual, `is`(empty<WorkoutEntity>()))
    }

    @Test
    fun `workoutがupdateできることのテスト`() {
        val workout = WorkoutEntity(Date(), 1, 1)
        dao.insert(workout)

        val newWorkout = workout.copy(trainingId = 2)
        dao.update(newWorkout)

        val actual: List<WorkoutEntity> = dao.load()

        assertThat(actual.size, `is`(1))
        assertThat(actual[0].trainingId, `is`(2))
    }
}