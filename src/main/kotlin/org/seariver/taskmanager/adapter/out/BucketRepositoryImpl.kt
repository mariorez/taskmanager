package org.seariver.taskmanager.adapter.out

import org.seariver.taskmanager.application.domain.Bucket
import org.seariver.taskmanager.application.domain.Name
import org.seariver.taskmanager.application.port.out.BucketRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.sql.DataSource

@Repository
class BucketRepositoryImpl(
    private val datasource: DataSource
) : BucketRepository {

    override fun create(bucket: Bucket) {

        val sql = """
            INSERT INTO bucket(external_id, position, name)
            values (?, ?, ?)
            """.trimIndent()

        datasource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setObject(1, bucket.externalId)
                stmt.setDouble(2, bucket.position)
                stmt.setString(3, bucket.name.name)
                stmt.executeUpdate()
            }
        }
    }

    override fun findByExternalId(externalId: UUID): Bucket? {

        var result: Bucket? = null

        val sql = """
            SELECT id, external_id, position, name
            FROM bucket
            WHERE external_id = ?
            """.trimIndent()

        datasource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, externalId.toString())
                stmt.executeQuery().use { rs ->
                    while (rs.next()) {
                        result = Bucket(
                            rs.getInt("id"),
                            UUID.fromString(rs.getString("external_id")),
                            rs.getDouble("position"),
                            Name(rs.getString("name"))
                        )
                    }
                }
            }
        }

        return result
    }
}
