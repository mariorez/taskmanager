package org.seariver.taskmanager.application.usecase

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.seariver.taskmanager.application.domain.Bucket
import org.seariver.taskmanager.application.domain.Title
import org.seariver.taskmanager.application.port.out.BucketRepository
import java.util.*
import kotlin.random.Random

class CreateBucketHandlerTest {

    @Test
    fun `GIVEN a valid data WHEN creating bucket MUST persist in Database`() {

        // given
        val externalId = UUID.randomUUID()
        val position = Random.nextDouble()
        val title = Title("TODO")
        val event = CreateBucket(externalId, position, title)
        val repository = mock<BucketRepository>()

        // when
        CreateBucketHandler(repository).handle(event)

        // then
        argumentCaptor<Bucket>().apply {
            verify(repository).create(capture())
            assertThat(firstValue.externalId).isEqualTo(externalId)
            assertThat(firstValue.position).isEqualTo(position)
            assertThat(firstValue.title).isEqualTo(title)
        }
    }
}
