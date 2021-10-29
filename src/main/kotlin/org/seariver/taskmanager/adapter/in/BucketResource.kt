package org.seariver.taskmanager.adapter.`in`

import org.seariver.taskmanager.application.domain.Title
import org.seariver.taskmanager.application.port.`in`.ServiceBus
import org.seariver.taskmanager.application.usecase.CreateBucket
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
    private val serviceBus: ServiceBus
) {

    @PostMapping
    fun create(@RequestBody request: BucketRequest): ResponseEntity<Void> {

        serviceBus.execute(
            CreateBucket(
                UUID.fromString(request.id),
                request.position,
                Title(request.title)
            )
        )

        return ResponseEntity(CREATED)
    }
}

data class BucketRequest(
    val id: String,
    val position: Double,
    val title: String
)
