package org.seariver.taskmanager.adapter.`in`

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import java.util.*
import kotlin.random.Random

@SpringBootTest
class BucketResourceTest(val mockMvc: MockMvc) {

    @Test
    fun `GIVEN a valid bucket payload MUST return Bucket created`() {

        // given
        val externalId = UUID.randomUUID().toString()
        val position = Random.nextDouble()
        val name = "Todo"
        val payload = """
        {
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