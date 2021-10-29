package org.seariver.taskmanager.application.usecase

import org.seariver.taskmanager.application.domain.Bucket
import org.seariver.taskmanager.application.port.`in`.Handler
import org.seariver.taskmanager.application.port.out.BucketRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreateBucketHandler(
    private val repository: BucketRepository
) : Handler<CreateBucket> {

    override fun handle(event: CreateBucket) {
        repository.create(
            Bucket(
                externalId = UUID.fromString(event.id),
                position = event.position,
                title = event.title
            )
        )
    }
}
