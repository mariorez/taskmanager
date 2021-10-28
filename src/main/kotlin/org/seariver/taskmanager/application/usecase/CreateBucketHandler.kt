package org.seariver.taskmanager.application.usecase

import org.seariver.taskmanager.application.domain.Bucket
import org.seariver.taskmanager.application.port.out.BucketRepository
import org.springframework.stereotype.Service

@Service
class CreateBucketHandler(
    private val repository: BucketRepository
) {

    fun handle(command: CreateBucketCommand) {
        repository.create(
            Bucket(
                id = command.id,
                position = command.position,
                name = command.name
            )
        )
    }
}
