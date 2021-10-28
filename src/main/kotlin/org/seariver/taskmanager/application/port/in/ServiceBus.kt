package org.seariver.taskmanager.application.port.`in`

import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class ServiceBus(
    private val context: ApplicationContext,
    private val eventPublisher: ApplicationEventPublisher
) {
    companion object {
        private val logger = LoggerFactory.getLogger(ServiceBus::class.java)
    }

    fun execute(event: Event) {
        try {
            run(event)
        } catch (exception: Throwable) {
            event.exception = exception
            throw exception
        } finally {
            doObserve(event)
            eventPublisher.publishEvent(event)
        }
    }

    private fun run(event: Event) {

        val canonicalEvent = event.eventName
            .replace("Command", "Handler")
            .replace("Query", "Handler")
            .replaceFirstChar { it.lowercase() }

        (context.getBean(canonicalEvent) as Handler<Event>).handle(event)
    }

    private fun doObserve(event: Event) {

        val message = mutableMapOf(
            "eventName" to event.eventName,
            "eventData" to event.sanitizedData()
        )

        when (event.exception) {
            null -> logger.info(message.toString())
            else -> logger.error(message.toString(), event.exception)
        }
    }
}
