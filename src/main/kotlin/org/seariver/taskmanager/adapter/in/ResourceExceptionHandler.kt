package org.seariver.taskmanager.adapter.`in`

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

        return ResponseEntity.badRequest().body(violations)
    }
}

internal data class FieldValidationError(
    val field: String,
    val detail: String
)
