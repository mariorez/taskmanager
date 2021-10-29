package org.seariver.taskmanager.application.usecase

import org.seariver.taskmanager.application.domain.Title
import org.seariver.taskmanager.application.port.`in`.Event
import java.util.*

data class CreateBucket(
    val externalId: UUID,
    val position: Double,
    val title: Title
) : Event() {

    override fun sanitizedData(): Map<String, Any> {
        return mapOf(
            "externalId" to externalId,
            "position" to position,
            "title" to title.title
        )
    }
}
