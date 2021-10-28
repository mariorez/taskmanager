package org.seariver.taskmanager.application.port.`in`

interface Handler<T : Event> {

    fun handle(event: T)
}
