package org.seariver.taskmanager.application.port.`in`

import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class EventBus(
    private val context: ApplicationContext,
    private val eventPublisher: ApplicationEventPublisher
) {

    private val logger = LoggerFactory.getLogger(EventBus::class.java)

    fun execute(event: Event) {
        try {
            run(event)
        } catch (exception: Throwable) {
            event.exception = exception
            throw exception
        } finally {
            observe(event)
            eventPublisher.publishEvent(event)
        }
    }

    private fun run(event: Event) {
        val beanName = "${event.eventName.replaceFirstChar { it.lowercase() }}Handler"
        @Suppress("UNCHECKED_CAST")
        (context.getBean(beanName) as Handler<Event>).handle(event)
    }

    private fun observe(event: Event) {

        val message = "eventName: ${event.eventName}, eventData: ${event.sanitizedData()}"

        when (event.exception) {
            null -> if (logger.isInfoEnabled) logger.info(message)
            else -> if (logger.isErrorEnabled) logger.error(
                "$message, error: ${event.exception!!.message}",
                event.exception
            )
        }
    }
}
