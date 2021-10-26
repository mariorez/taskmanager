package org.seariver.taskmanager.adapter.`in`

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import java.util.*
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
        val name = "Todo"
        val payload = """{
            "id": "$externalId",
            "position": $position,
            "name": "$name"
        }"""

        // when
        mockMvc.post("/v1/buckets") {
            contentType = APPLICATION_JSON
            content = payload
        }.andExpect {
            status { isCreated() }
        }
    }
}
