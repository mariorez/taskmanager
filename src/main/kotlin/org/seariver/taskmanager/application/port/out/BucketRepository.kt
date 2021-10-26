package org.seariver.taskmanager.application.port.out

import org.seariver.taskmanager.application.domain.Bucket
import java.util.*

interface BucketRepository {

    fun create(bucket: Bucket)

    fun findByExternalId(externalId: UUID): Bucket?
}
