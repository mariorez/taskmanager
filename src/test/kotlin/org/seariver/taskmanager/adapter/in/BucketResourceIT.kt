package org.seariver.taskmanager.adapter.`in`

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
        externalId: UUID,
        title: String
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
        }.andDo {
            print()
        }
    }

    companion object {
        @JvmStatic
        fun provideInvalidData(): Stream<Arguments> {

            val validUUID = UUID.randomUUID()
            val validTitle = "1.1) TODO"

            return Stream.of(
                //Arguments.of("", validTitle),
                Arguments.of(validUUID, ""),
                Arguments.of(validUUID, " "),
                Arguments.of(validUUID, "cannot be greater than hundred chars".repeat(3))
            )
        }
    }
}
