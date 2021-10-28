package org.seariver.taskmanager.listener

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import org.seariver.taskmanager.application.port.`in`.Event
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
open class MetricsListener(
    private val meterRegistry: MeterRegistry
) {

    @Async
    @EventListener
    open fun onAnyEvent(event: Event) {
        Counter.builder(event.eventName)
            .tag(
                "type",
                if (event.eventName.indexOf("Command") != -1) "stateful" else "stateless"
            )
            .tag(
                "result",
                if (event.exception == null) "success" else "error",
            )
            .register(meterRegistry)
            .increment()
    }
}
