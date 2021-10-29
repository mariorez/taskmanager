package org.seariver.taskmanager.application.usecase

import org.seariver.taskmanager.application.domain.Title
import org.seariver.taskmanager.application.port.`in`.Event
import org.seariver.taskmanager.application.util.SelfValidating
import org.seariver.taskmanager.application.util.SelfValidating.Companion.INVALID_UUID
import org.seariver.taskmanager.application.util.SelfValidating.Companion.UUID_FORMAT
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class CreateBucket(
    @field:NotBlank
    @field:Pattern(regexp = UUID_FORMAT, message = INVALID_UUID)
    val id: String,
    val position: Double,
    val title: Title
) : Event(), SelfValidating<CreateBucket> {

    init {
        validateSelf()
    }

    override fun sanitizedData(): Map<String, Any> {
        return mapOf(
            "id" to id,
            "position" to position,
            "title" to title.title
        )
    }
}
