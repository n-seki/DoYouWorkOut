package seki.com.doyouworkout.usecase

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Completable
import org.junit.Test
import seki.com.doyouworkout.data.repository.Repository
import seki.com.doyouworkout.ui.Training
import seki.com.doyouworkout.usecase.impl.UpdateTrainingUseCaseImp

class UpdateTrainingUseCaseTest {

    @Test
    fun `if update training task is success, then return true`() {
        val trainingList =
                listOf(
                        Training(id = 1, name = "腹筋", isUsed = true,
                                isCustom = false ,isDeleted = false)
                )

        val repository = mock<Repository> {
            on { updateTraining(trainingList) }.doReturn(Completable.complete())
        }

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

        val repository = mock<Repository> {
            on { updateTraining(trainingList) }.doReturn(Completable.error(RuntimeException()))
        }

        UpdateTrainingUseCaseImp(repository)
                .execute(trainingList)
                .test()
                .await()
                .assertValue(false)
    }

}