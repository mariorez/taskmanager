package org.seariver.taskmanager.adapter.out

import one.microstream.reference.Lazy
import one.microstream.storage.embedded.types.EmbeddedStorageManager
import org.seariver.taskmanager.application.domain.Bucket
import org.seariver.taskmanager.application.port.out.BucketRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class BucketRepositoryImpl(
    private val storageManager: EmbeddedStorageManager
) : BucketRepository {

    init {
        if (storageManager.root() == null) {
            storageManager.setRoot(BucketRoot())
            storageManager.storeRoot()
        }
    }

    override fun create(bucket: Bucket) {
        val bucketRoot = storageManager.root() as BucketRoot
        bucketRoot.add(bucket)
        storageManager.store(bucketRoot.getAll())
    }

    override fun findById(id: UUID): Bucket? {
        val bucketRoot = storageManager.root() as BucketRoot
        return bucketRoot.get(id)
    }
}

class BucketRoot {

    private var bucketCollection: Lazy<MutableMap<UUID, Bucket>>? = null

    fun getAll(): MutableMap<UUID, Bucket> {
        if (Lazy.get(bucketCollection) == null)
            bucketCollection = Lazy.Reference(mutableMapOf())

        return Lazy.get(bucketCollection)
    }

    fun get(id: UUID): Bucket? {
        return getAll().getOrDefault(id, null)
    }

    fun add(bucket: Bucket) {
        getAll()[bucket.id] = bucket
    }
}
