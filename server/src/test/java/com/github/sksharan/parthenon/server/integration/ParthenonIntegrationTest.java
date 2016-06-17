package com.github.sksharan.parthenon.server.integration;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.sksharan.parthenon.server.Parthenon;

/** Base class for integration testing that allows subclasses to use beans
 *  from the main application. Instead of using the production database
 *  defined in src/main/resources/application.properties, a test (in-memory)
 *  database is used as defined in src/test/resources/application.properties. */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Parthenon.class)
public abstract class ParthenonIntegrationTest {
}
