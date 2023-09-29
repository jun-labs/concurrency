package project.lock.jpa.test;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import project.lock.jpa.common.annotation.IntegrationTest;

@IntegrationTest
public abstract class IntegrationTestBase {

    @Autowired
    private RdbInitializationConfiguration databaseInitialization;

    protected IntegrationTestBase() {
    }

    @BeforeEach
    void setUp() {
        databaseInitialization.truncateAllEntity();
        databaseInitialization.insertData();
    }
}
