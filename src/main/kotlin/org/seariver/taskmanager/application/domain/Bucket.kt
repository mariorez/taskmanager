package org.seariver.taskmanager.application.domain

import java.util.*

class Bucket(
    val id: Int? = null,
    val externalId: UUID,
    val position: Double,
    val title: Title
)
