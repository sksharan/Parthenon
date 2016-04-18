package com.github.sksharan.parthenon.server;

import org.flywaydb.core.Flyway;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class ParthenonTest {
    protected EmbeddedDatabase db;

    @Before
    public void setUp() throws Exception {
        db = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
        Flyway flyway = new Flyway();
        flyway.setDataSource(db);
        flyway.migrate();
    }

    @After
    public void tearDown() {
        db.shutdown();
    }

    @Test
    public void noop() {
    }

}
