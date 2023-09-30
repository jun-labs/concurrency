package project.lock.rdblock.test;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import project.lock.rdblock.common.annotation.IntegrationTest;

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
