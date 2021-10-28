package org.seariver.taskmanager.config

import one.microstream.storage.embedded.types.EmbeddedStorage
import one.microstream.storage.embedded.types.EmbeddedStorageManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.nio.file.Paths

@Configuration
open class MicrostreamConfig {

    @Bean
    open fun storageManager(
        @Value("\${microstream.store.location}") location: String
    ): EmbeddedStorageManager {
        return EmbeddedStorage.start(Paths.get(location))
    }
}
