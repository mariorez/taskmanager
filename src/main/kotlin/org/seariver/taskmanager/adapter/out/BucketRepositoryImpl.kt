package org.seariver.taskmanager.adapter.out

import org.seariver.taskmanager.application.domain.Bucket
import org.seariver.taskmanager.application.domain.Title
import org.seariver.taskmanager.application.port.out.BucketRepository
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service
import java.util.*
import javax.sql.DataSource

@Service
class BucketRepositoryImpl(
    datasource: DataSource
) : BucketRepository {

    private val jdbcTemplate = NamedParameterJdbcTemplate(datasource)

    override fun create(bucket: Bucket) {

        val sql = """
            INSERT INTO bucket(external_id, position, title)
            values (:externalId, :position, :title)
            """

        val params = mapOf(
            "externalId" to bucket.externalId,
            "position" to bucket.position,
            "title" to bucket.title.title
        )

        jdbcTemplate.update(sql, params)
    }

    override fun findByExternalId(externalId: UUID): Bucket? {

        var result: Bucket? = null

        val sql = """
            SELECT id, external_id, position, title
            FROM bucket
            WHERE external_id = :externalId
            """

        val params = mapOf("externalId" to externalId)

        jdbcTemplate.query(sql, params) {
            result = Bucket(
                it.getInt("id"),
                UUID.fromString(it.getString("external_id")),
                it.getDouble("position"),
                Title(it.getString("title"))
            )
        }

        return result
    }
}
