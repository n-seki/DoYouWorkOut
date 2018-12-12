package seki.com.doyouworkout.usecase

import io.reactivex.Completable
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import seki.com.doyouworkout.data.repository.Repository
import seki.com.doyouworkout.ui.Training
import seki.com.doyouworkout.usecase.impl.UpdateTrainingUseCaseImp
import java.lang.RuntimeException

class UpdateTrainingUseCaseTest {

    @Test
    fun `if update training task is success, then return true`() {
        val trainingList =
                listOf(
                        Training(id = 1, name = "腹筋", isUsed = true,
                                isCustom = false ,isDeleted = false)
                )

        val repository = mock(Repository::class.java)
        `when`(repository.updateTraining(trainingList)).thenReturn(
                Completable.complete()
        )

        UpdateTrainingUseCaseImp(repository)
                .execute(trainingList)
                .test()
                .await()
                .assertValue(true)
    }

    @Test
    fun `if update training task is failure, then return false`() {
        val trainingList =
                listOf(
                        Training(id = 1, name = "腹筋", isUsed = true,
                                isCustom = false ,isDeleted = false)
                )

        val repository = mock(Repository::class.java)
        `when`(repository.updateTraining(trainingList)).thenReturn(
                Completable.error(RuntimeException())
        )

        UpdateTrainingUseCaseImp(repository)
                .execute(trainingList)
                .test()
                .await()
                .assertValue(false)
    }

}