package org.seariver.taskmanager.application.usecase

import org.seariver.taskmanager.application.domain.Bucket
import org.seariver.taskmanager.application.port.`in`.Handler
import org.seariver.taskmanager.application.port.out.BucketRepository
import org.springframework.stereotype.Service

@Service
class CreateBucketHandler(
    private val repository: BucketRepository
) : Handler<CreateBucketCommand> {

    override fun handle(event: CreateBucketCommand) {
        repository.create(
            Bucket(
                externalId = event.externalId,
                position = event.position,
                name = event.name
            )
        )
    }
}
