package org.seariver.taskmanager.application.port.`in`

abstract class Event {

    val eventName: String = this.javaClass.simpleName
    var exception: Throwable? = null

    abstract fun sanitizedData(): Map<String, Any>

    override fun toString(): String {
        return sanitizedData().toString()
    }
}
