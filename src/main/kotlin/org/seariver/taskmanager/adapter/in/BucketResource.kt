package org.seariver.taskmanager.adapter.`in`

import org.seariver.taskmanager.application.domain.Name
import org.seariver.taskmanager.application.usecase.CreateBucketCommand
import org.seariver.taskmanager.application.usecase.CreateBucketHandler
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/v1/buckets")
class BucketResource(
    val createBucketHandler: CreateBucketHandler
) {

    @PostMapping
    fun create(@RequestBody request: BucketRequest): ResponseEntity<Void> {

        createBucketHandler.handle(
            CreateBucketCommand(
                UUID.fromString(request.id),
                request.position,
                Name(request.name)
            )
        )

        return ResponseEntity(CREATED)
    }
}

data class BucketRequest(
    val id: String,
    val position: Double,
    val name: String
)
