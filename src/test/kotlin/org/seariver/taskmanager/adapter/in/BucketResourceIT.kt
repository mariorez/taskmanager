package org.seariver.taskmanager.adapter.`in`

import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import java.util.*
import java.util.stream.Stream
import kotlin.random.Random

@SpringBootTest
@AutoConfigureMockMvc
class BucketResourceIT(
    @Autowired val mockMvc: MockMvc
) {

    @Test
    fun `GIVEN a valid payload MUST create a bucket successfully`() {

        // given
        val externalId = UUID.randomUUID()
        val position = Random.nextDouble()
        val title = "1.2 - TODO"
        val payload = """{
            "id": "$externalId",
            "position": $position,
            "title": "$title"
        }"""

        // when
        mockMvc.post("/v1/buckets") {
            contentType = APPLICATION_JSON
            content = payload
        }.andExpect {
            status { isCreated() }
        }
    }

    @ParameterizedTest
    @MethodSource("provideInvalidData")
    fun `GIVEN invalid data MUST return bad request`(
        externalId: String,
        title: String,
        errorsFields: Array<String>,
        errorsDetails: Array<String>
    ) {
        // given
        val position = Random.nextDouble()
        val payload = """{
            "id": "$externalId",
            "position": $position,
            "title": "$title"
        }"""

        // when
        mockMvc.post("/v1/buckets") {
            contentType = APPLICATION_JSON
            content = payload
        }.andExpect {
            status { isBadRequest() }
            content { contentType(APPLICATION_JSON) }
        }.andExpect {
            jsonPath("$.message", `is`("Invalid data"))
            jsonPath("$.errors[*].field", containsInAnyOrder(*errorsFields))
            jsonPath("$.errors[*].detail", containsInAnyOrder(*errorsDetails))
        }
    }

    companion object {
        @JvmStatic
        fun provideInvalidData(): Stream<Arguments> {

            val validUUID = UUID.randomUUID().toString()
            val validTitle = "1.1) TODO"

            // error messages
            val notBlank = "must not be blank"
            val invalidSize = "size must be between 1 and 100"
            val invalidIdFormat = "invalid UUID format"

            return Stream.of(
                // ID validations
                Arguments.of("", validTitle, arrayOf("id", "id"), arrayOf(invalidIdFormat, notBlank)),
                Arguments.of("bla", validTitle, arrayOf("id"), arrayOf(invalidIdFormat)),
                // Title validations
                Arguments.of(validUUID, "", arrayOf("title", "title"), arrayOf(notBlank, invalidSize)),
                Arguments.of(validUUID, " ", arrayOf("title"), arrayOf(notBlank)),
                Arguments.of(validUUID, "not > 100".repeat(12), arrayOf("title"), arrayOf(invalidSize))
            )
        }
    }
}
