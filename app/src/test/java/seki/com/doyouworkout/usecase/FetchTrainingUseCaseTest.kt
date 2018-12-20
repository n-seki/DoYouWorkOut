package seki.com.doyouworkout.usecase

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito.*
import seki.com.doyouworkout.data.db.entity.TrainingEntity
import seki.com.doyouworkout.data.db.mapper.toUIData
import seki.com.doyouworkout.data.repository.Repository
import seki.com.doyouworkout.usecase.impl.FetchTrainingUseCaseImp

class FetchTrainingUseCaseTest {

    @Test
    fun `Trainingのリストが取得できること`() {
        val trainingEntityList = listOf(
                TrainingEntity(id = 0, name = "腹筋"),
                TrainingEntity(id = 1, name = "腕立て伏せ"),
                TrainingEntity(id = 2, name = "背筋"),
                TrainingEntity(id = 3, name = "スクワット")
        )

        val repository = mock<Repository> {
            on { getAllTrainingList() }.doReturn(Single.just(trainingEntityList))
        }

        val expected = trainingEntityList.map { it.toUIData() }

        FetchTrainingUseCaseImp(repository)
                .execute()
                .test()
                .await()
                .assertValue(expected)

        verify(repository).putTrainingCache(trainingEntityList)
    }
}