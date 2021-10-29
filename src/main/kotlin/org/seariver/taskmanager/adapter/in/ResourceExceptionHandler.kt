package org.seariver.taskmanager.adapter.`in`

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.stream.Collectors
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class ResourceExceptionHandler {

    @ExceptionHandler(ConstraintViolationException::class)
    fun onConstraintViolationException(exception: ConstraintViolationException): ResponseEntity<Any> {

        val violations: MutableList<FieldValidationError> = exception
            .constraintViolations
            .stream()
            .map { violation ->
                val fieldPath = violation.propertyPath.toString()
                val fieldName = fieldPath.substring(fieldPath.lastIndexOf(".") + 1)
                FieldValidationError(fieldName, violation.message)
            }
            .collect(Collectors.toList())

        return getResponseEntity("Invalid data", BAD_REQUEST, violations)
    }

    private fun getResponseEntity(
        message: String,
        status: HttpStatus,
        detailedErrors: List<FieldValidationError>?
    ): ResponseEntity<Any> {
        val errors: MutableMap<String, Any> = mutableMapOf()
        errors["message"] = message
        if (!detailedErrors.isNullOrEmpty()) errors["errors"] = detailedErrors
        return ResponseEntity.status(status).body(errors)
    }
}

internal data class FieldValidationError(
    val field: String,
    val detail: String
)
