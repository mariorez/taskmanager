package org.seariver.taskmanager.adapter.out

import helper.DataSourceHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.seariver.taskmanager.application.domain.Bucket
import org.seariver.taskmanager.application.domain.Title
import org.seariver.taskmanager.application.port.out.BucketRepository
import java.util.*
import javax.sql.DataSource
import kotlin.random.Random

class BucketRepositoryImplTest {

    private val dataSource = DataSourceHelper()

    @Test
    fun `BucketRepositoryImpl MUST implement BucketRepository interface`() {
        val repository = BucketRepositoryImpl(mock(DataSource::class.java))
        assertThat(repository).isInstanceOf(BucketRepository::class.java)
    }

    @Test
    fun `GIVEN valid bucket entity WHEN create new bucket THEN persist in database`() {

        // given
        val bucket = Bucket(
            externalId = UUID.randomUUID(),
            position = Random.nextDouble(),
            title = Title("Todo")
        )

        // when
        val repository = BucketRepositoryImpl(dataSource)
        repository.create(bucket)

        // then
        val actualBucket = repository.findByExternalId(bucket.externalId)
        assertThat(actualBucket).isNotNull
        actualBucket?.apply {
            assertThat(position).isEqualTo(bucket.position)
            assertThat(title.toString()).isEqualTo(bucket.title.toString())
        }
    }
}