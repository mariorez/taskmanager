package org.seariver.taskmanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TaskmanagerApplication

fun main(args: Array<String>) {
    runApplication<TaskmanagerApplication>(*args)
}
