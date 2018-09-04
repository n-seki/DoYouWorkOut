package seki.com.doyouworkout.data.db

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.filters.SmallTest
import android.support.test.runner.AndroidJUnit4
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

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
        val training = listOf(TrainingEntity(1, 1, true))
        dao.insert(training)

        dao.select()
                .subscribeOn(Schedulers.trampoline())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { assertThat(it, `is`(training)) }
                .dispose()
    }

    @Test
    fun `trainingをupdateできること`() {
        val training = listOf(TrainingEntity(1, 1, true))
        dao.insert(training)

        val newTraining = listOf(
                training[0].copy(
                        trainingNameId = 2,
                        used = false,
                        custom = true,
                        customName = "test",
                        delete = true)
        )

        dao.update(newTraining)

        dao.select()
                .subscribeOn(Schedulers.trampoline())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { assertThat(it, `is`(newTraining)) }
                .dispose()
    }
}