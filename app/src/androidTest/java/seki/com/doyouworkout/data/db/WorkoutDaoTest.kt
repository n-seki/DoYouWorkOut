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
import java.text.SimpleDateFormat
import java.util.*

@RunWith(AndroidJUnit4::class) @SmallTest
class WorkoutDaoTest {

    private lateinit var db: AppDataBase
    private lateinit var dao: WorkoutDao

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
    fun `insertしたworkoutがselectできること`() {
        val workout = listOf(
                WorkoutEntity(Date(), 1, 1)
        )

        dao.insert(workout)

        val actual: List<WorkoutEntity> = dao.load(Date())
        assertThat(actual[0].trainingId, `is`(workout.trainingId))
    }

    @Test
    fun `insertしたworkoutをdeleteできること`() {
        val workout = listOf(
                WorkoutEntity(Date(), 1, 1)
        )

        dao.insert(workout)

        dao.delete(workout[0])

        val actual: List<WorkoutEntity> = dao.load(Date())
        assertThat(actual, `is`(empty<WorkoutEntity>()))
    }

    @Test
    fun `insertしたworkoutをupdateできること`() {
        val workout = listOf(
                WorkoutEntity(Date(), 1, 1)
        )

        dao.insert(workout)

        val newWorkout = workout[0].copy(count = 4)
        dao.update(newWorkout)

        val actual: List<WorkoutEntity> = dao.load(Date())

        assertThat(actual.size, `is`(1))
        assertThat(actual[0].count, `is`(4))
    }

    @Test
    fun `指定日よりも前のworkoutがselectできること`() {
        val format = SimpleDateFormat("yyyyMMDD")
        val today = format.parse("20180624")

        val yesterdayWorkout = WorkoutEntity(format.parse("20180623"), 1, 1)
        val todayWorkout = WorkoutEntity(today, 2, 2)
        val tomorrowWorkout = WorkoutEntity(format.parse("20180625"), 3, 3)

        dao.insert(yesterdayWorkout, todayWorkout, tomorrowWorkout)

        val actual: List<WorkoutEntity> = dao.load(today)

        assertThat(actual.size, `is`(2))
        assertThat(actual[0], `is`(yesterdayWorkout))
        assertThat(actual[1], `is`(todayWorkout))
    }
}