package org.seariver.taskmanager.application.usecase

import org.seariver.taskmanager.application.domain.Name
import java.util.*

data class CreateBucketCommand(
    val externalId: UUID,
    val position: Double,
    val name: Name
)
