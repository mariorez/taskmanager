package org.seariver.taskmanager.application.port.out

import org.seariver.taskmanager.application.domain.Bucket
import java.util.*

interface BucketRepository {

    fun create(capture: Bucket)

    fun findByExternalId(externalId: UUID): Bucket?
}
