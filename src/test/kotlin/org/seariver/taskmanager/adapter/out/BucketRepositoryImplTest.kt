package org.seariver.taskmanager.adapter.out

import one.microstream.storage.embedded.types.EmbeddedStorage
import one.microstream.storage.embedded.types.EmbeddedStorageManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.mockito.Mockito.mock
import org.seariver.taskmanager.application.domain.Bucket
import org.seariver.taskmanager.application.domain.Name
import org.seariver.taskmanager.application.port.out.BucketRepository
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import kotlin.random.Random

class BucketRepositoryImplTest {

    @TempDir
    @JvmField
    var storagePath: Path = Paths.get("storage/test/unit")

    @Test
    fun `BucketRepositoryImpl MUST implement BucketRepository interface`() {
        val repository = BucketRepositoryImpl(mock(EmbeddedStorageManager::class.java))
        assertThat(repository).isInstanceOf(BucketRepository::class.java)
    }

    @Test
    fun `GIVEN valid bucket entity WHEN create new bucket THEN persist in database`() {

        // given
        val bucket = Bucket(
            id = UUID.randomUUID(),
            position = Random.nextDouble(),
            name = Name("Todo")
        )

        // when
        val repository = BucketRepositoryImpl(EmbeddedStorage.start(storagePath))
        repository.create(bucket)

        // then
        val actualBucket = repository.findById(bucket.id)
        assertThat(actualBucket).isNotNull
        actualBucket?.apply {
            assertThat(position).isEqualTo(bucket.position)
            assertThat(name.toString()).isEqualTo(bucket.name.toString())
        }
    }
}
