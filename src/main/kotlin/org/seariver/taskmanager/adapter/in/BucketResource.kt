package org.seariver.taskmanager.adapter.`in`

import org.seariver.taskmanager.application.domain.Name
import org.seariver.taskmanager.application.usecase.CreateBucketCommand
import org.seariver.taskmanager.application.usecase.CreateBucketHandler
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController("v1/buckets")
class BucketResource(
    val createBucketHandler: CreateBucketHandler
) {

    @PostMapping
    fun create(@RequestBody payload: BucketInput): ResponseEntity<Void> {

        createBucketHandler.handle(
            CreateBucketCommand(
                UUID.fromString(payload.externalId),
                payload.position,
                Name(payload.name)
            )
        )

        return ResponseEntity(CREATED)
    }
}

data class BucketInput(
    val externalId: String,
    val position: Double,
    val name: String
)
