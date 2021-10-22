package org.seariver.taskmanager.application.usecase

import org.seariver.taskmanager.application.domain.Bucket
import org.seariver.taskmanager.application.port.out.BucketRepository

class CreateBucketHandler(private val repository: BucketRepository) {

    fun handle(command: CreateBucketCommand) {
        repository.create(
            Bucket(
                externalId = command.externalId,
                position = command.position,
                name = command.name
            )
        )
    }
}
