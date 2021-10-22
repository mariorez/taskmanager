package org.seariver.taskmanager.application.port.out

import org.seariver.taskmanager.application.domain.Bucket

interface BucketRepository {

    fun create(capture: Bucket)
}
