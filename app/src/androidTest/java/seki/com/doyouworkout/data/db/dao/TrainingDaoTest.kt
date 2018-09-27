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
import seki.com.doyouworkout.data.db.entity.TrainingEntity

@RunWith(AndroidJUnit4::class) @SmallTest
class TrainingDaoTest {

    private lateinit var db: AppDataBase
    private lateinit var dao: TrainingDao

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
        val training = listOf(TrainingEntity(id = 1, name = "test"))
        dao.insert(training)

        dao.select()
                .test()
                .await()
                .assertValueCount(1)
                .assertValue(training)
    }

    @Test
    fun `trainingをupdateできること`() {
        val training = listOf(TrainingEntity(id = 1, name = "test"))
        dao.insert(training)

        val newTraining = listOf(TrainingEntity(id = 1, name = "replace"))
        dao.insert(newTraining)

        dao.select()
                .test()
                .await()
                .assertValueCount(1)
                .assertValue(newTraining)
    }
}