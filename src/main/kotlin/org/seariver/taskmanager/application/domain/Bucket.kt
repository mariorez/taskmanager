package org.seariver.taskmanager.application.domain

import java.util.*

class Bucket(
    val id: UUID,
    val position: Double,
    val name: Name
) {
    override fun toString(): String {
        return "Bucket(id=$id, position=$position, name=$name)"
    }
}



