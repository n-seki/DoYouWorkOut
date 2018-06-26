package seki.com.doyouworkout.data.db

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.filters.SmallTest
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class) @SmallTest
class TrainingDaoTest {

    lateinit var db: AppDataBase
    lateinit var dao: TrainingDao

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java).build()
        dao = db.trainingDao()
    }

    @After
    fun deleteDb() {
        db.close()
    }

    @Test
    fun `インサートしたtrainingがselectできること`() {
        val training = TrainingEntity(1, 1, true)
        dao.insert(training)

        val actual = dao.loadAll()

        assertThat(actual.size, `is`(1))
        assertThat(actual[0], `is`(training))
    }

    @Test
    fun `trainingをupdateできること`() {
        val training = TrainingEntity(1, 1, true)
        dao.insert(training)

        val newTraining = training.copy(
                trainingNameId = 2,
                used = false,
                custom = true,
                customName = "test",
                delete = true)

        dao.update(listOf(newTraining))

        val actual = dao.loadAll()
        assertThat(actual.size, `is`(1))
        assertThat(actual[0], `is`(newTraining))
    }
}