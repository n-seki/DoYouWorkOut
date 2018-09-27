package seki.com.doyouworkout.usecase

import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito.*
import seki.com.doyouworkout.data.db.entity.TrainingEntity
import seki.com.doyouworkout.data.db.mapper.toUIData
import seki.com.doyouworkout.data.repository.Repository

class TrainingUseCaseTest {

    private val mockRepository = mock(Repository::class.java)
    private val sut = TrainingUseCase(mockRepository, TestSchedulersProvider)

    @Test
    fun `Trainingのリストが取得できること`() {

        val trainingEntityList = listOf(
                TrainingEntity(id = 0, name = "腹筋"),
                TrainingEntity(id = 1, name = "腕立て伏せ"),
                TrainingEntity(id = 2, name = "背筋"),
                TrainingEntity(id = 3, name = "スクワット")
        )


        `when`(mockRepository.getAllTrainingList()).thenReturn(
                Single.create { emitter -> emitter.onSuccess(trainingEntityList) }
        )

        val expected = trainingEntityList.map { it.toUIData() }

        sut.fetchTrainingList()
                .test()
                .await()
                .assertValue(expected)

        verify(mockRepository).putTrainingCache(trainingEntityList)
    }
}