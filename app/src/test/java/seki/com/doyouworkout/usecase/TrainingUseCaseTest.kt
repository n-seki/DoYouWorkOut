package seki.com.doyouworkout.usecase

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import seki.com.doyouworkout.data.cache.Cache
import seki.com.doyouworkout.data.db.mapper.toUIData
import seki.com.doyouworkout.data.repository.MockRepository

class TrainingUseCaseTest {

    private val mockCache = mock(Cache::class.java)
    private val mockRepository = MockRepository(mockCache)
    private val sut = TrainingUseCase(mockRepository, TestSchedulersProvider)

    @Test
    fun `Trainingのリストが取得できること`() {

        val expected = MockRepository.defaultTraining.map { it.toUIData() }

        sut.fetchTrainingList()
                .test()
                .await()
                .assertValue(expected)

        verify(mockCache).updateTraining(MockRepository.defaultTraining)
    }
}