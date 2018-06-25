package seki.com.doyouworkout.data.db

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test

class WorkoutEntityTest {

    @Test
    fun `used = 1の場合にisUsedがtrueとなること`() {
        val training = TrainingEntity(id = 1, trainingNameId = 1, used = 1)
        assertThat(training.isUsed, `is`(true))
    }

    @Test
    fun `used = 0の場合にisUsedがfalseとなること`() {
        val training = TrainingEntity(id = 1, trainingNameId = 1, used = 0)
        assertThat(training.isUsed, `is`(false))
    }

    @Test
    fun `custom = 1の場合にisCustomがtrueとなること`() {
        val training = TrainingEntity(id = 1, trainingNameId = 1, used = 1, custom = 1)
        assertThat(training.isCustom, `is`(true))
    }

    @Test
    fun `custom = 0の場合にisCustomがfalseとなること`() {
        val training = TrainingEntity(id = 1, trainingNameId = 1, used = 1, custom = 0)
        assertThat(training.isCustom, `is`(false))
    }

    @Test
    fun `delete = 1の場合にisDeletedがtrueとなること`() {
        val training = TrainingEntity(id = 1, trainingNameId = 1, used = 1, delete = 1)
        assertThat(training.isDeleted, `is`(true))
    }

    @Test
    fun `delete = 0の場合にisDeletedがfalseとなること`() {
        val training = TrainingEntity(id = 1, trainingNameId = 1, used = 1, delete = 0)
        assertThat(training.isDeleted, `is`(false))
    }
}