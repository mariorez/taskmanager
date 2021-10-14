package org.seariver.taskmanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class TaskmanagerApplication

fun main(args: Array<String>) {
    runApplication<TaskmanagerApplication>(*args)
}
