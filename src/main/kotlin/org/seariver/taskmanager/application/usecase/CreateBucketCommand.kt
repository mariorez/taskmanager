package org.seariver.taskmanager.application.usecase

import org.seariver.taskmanager.application.domain.Name
import java.util.*

data class CreateBucketCommand(
    val id: UUID,
    val position: Double,
    val name: Name
)
