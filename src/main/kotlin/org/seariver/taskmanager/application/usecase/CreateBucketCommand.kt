package org.seariver.taskmanager.application.usecase

import org.seariver.taskmanager.application.domain.Name
import org.seariver.taskmanager.application.port.`in`.Event
import java.util.*

data class CreateBucketCommand(
    val id: UUID,
    val position: Double,
    val name: Name
) : Event() {

    override fun sanitizedData(): Map<String, Any> {
        return mapOf(
            "id" to id,
            "position" to position,
            "name" to name.value
        )
    }
}
