package seki.com.doyouworkout.data.db.dao

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.filters.SmallTest
import android.support.test.runner.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import seki.com.doyouworkout.data.db.AppDataBase
import seki.com.doyouworkout.data.db.entity.WorkoutEntity
import java.text.SimpleDateFormat

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
        val format = SimpleDateFormat("yyyyMMDD")
        val date = format.parse("20180624")

        val workout = arrayListOf(WorkoutEntity(date, 1, 1))

        dao.insert(workout)

        dao.selectUntil(date)
                .test()
                .await()
                .assertValueCount(1)
                .assertValue(workout)
    }

    @Test
    fun `指定日よりも前のworkoutがselectできること`() {
        val format = SimpleDateFormat("yyyyMMDD")
        val today = format.parse("20180624")

        val workouts = listOf(
                WorkoutEntity(format.parse("20180623"), 1, 1),
                WorkoutEntity(today, 2, 2),
                WorkoutEntity(format.parse("20180625"), 3, 3)
        )

        dao.insert(workouts)

        dao.selectUntil(today)
                .test()
                .await()
                .assertValueCount(1)
                .assertValue { list ->
                    list == workouts.dropLast(1)
                }
    }
}