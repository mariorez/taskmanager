package org.seariver.taskmanager.adapter.out

import org.seariver.taskmanager.application.domain.Bucket
import org.seariver.taskmanager.application.domain.Name
import org.seariver.taskmanager.application.port.out.BucketRepository
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.util.*
import javax.sql.DataSource

@Repository
class BucketRepositoryImpl(
    datasource: DataSource
) : BucketRepository {

    private val jdbcTemplate = NamedParameterJdbcTemplate(datasource)

    override fun create(bucket: Bucket) {

        var result: Bucket? = null

        val sql = """
            INSERT INTO bucket(external_id, position, name)
            values (:externalId, :position, :name)
            """

        val params = mapOf(
            "externalId" to bucket.externalId,
            "position" to bucket.position,
            "name" to bucket.name.value
        )

        jdbcTemplate.update(sql, params)
    }

    override fun findByExternalId(externalId: UUID): Bucket? {

        var result: Bucket? = null

        val sql = """
            SELECT id, external_id, position, name
            FROM bucket
            WHERE external_id = :externalId
            """

        val params = mapOf("externalId" to externalId)

        jdbcTemplate.query(sql, params) {
            result = Bucket(
                it.getInt("id"),
                UUID.fromString(it.getString("external_id")),
                it.getDouble("position"),
                Name(it.getString("name"))
            )
        }

        return result
    }
}
