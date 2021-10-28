package org.seariver.taskmanager.application

import javax.validation.ConstraintViolationException
import javax.validation.Validation

interface SelfValidating<T> {

    fun validateSelf() {
        val factory = Validation.buildDefaultValidatorFactory()
        val validator = factory.validator

        @Suppress("UNCHECKED_CAST")
        val violations = validator.validate(this as T)

        if (violations.isNotEmpty())
            throw ConstraintViolationException(violations)
    }

    companion object {
        const val UUID_FORMAT = "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$"
        const val INVALID_UUID = "invalid UUID format"
    }
}
