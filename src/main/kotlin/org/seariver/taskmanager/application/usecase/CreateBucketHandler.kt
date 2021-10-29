package org.seariver.taskmanager.application.usecase

import org.seariver.taskmanager.application.domain.Bucket
import org.seariver.taskmanager.application.port.`in`.Handler
import org.seariver.taskmanager.application.port.out.BucketRepository
import org.springframework.stereotype.Service

@Service
class CreateBucketHandler(
    private val repository: BucketRepository
) : Handler<CreateBucket> {

    override fun handle(event: CreateBucket) {
        repository.create(
            Bucket(
                externalId = event.externalId,
                position = event.position,
                title = event.title
            )
        )
    }
}
