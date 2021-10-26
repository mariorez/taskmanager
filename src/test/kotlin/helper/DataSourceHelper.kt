package helper

import org.apache.commons.dbcp2.BasicDataSource

class DataSourceHelper : BasicDataSource() {
    init {
        url = "jdbc:h2:mem:DATABASE_TEST;" +
                "MODE=PostgreSQL;" +
                "INIT=RUNSCRIPT FROM 'src/main/resources/db/migration/V001__Initial_setup.sql'\\;"
    }
}
