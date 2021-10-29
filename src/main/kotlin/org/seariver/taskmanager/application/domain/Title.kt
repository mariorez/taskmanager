package org.seariver.taskmanager.application.domain

import org.seariver.taskmanager.application.util.SelfValidating
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@JvmInline
value class Title(
    @field:NotBlank
    @field:Size(min = 1, max = 100)
    val title: String
) : SelfValidating<Title> {
    init {
        validateSelf()
    }
}
