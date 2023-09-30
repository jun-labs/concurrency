package project.lock.rdblock.test;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import project.lock.rdblock.core.domain.member.entity.Member;
import project.lock.rdblock.core.domain.member.repository.MemberRepository;

@Component
public class RdbInitializationConfiguration {

    private static final String SET_FOREIGN_KEY_CHECKS_FALSE = "SET FOREIGN_KEY_CHECKS = 0";
    private static final String SET_FOREIGN_KEY_CHECKS_TRUE = "SET FOREIGN_KEY_CHECKS = 1";
    private static final String ALL_TABLE_NAMES =
        "SELECT table_name FROM information_schema.TABLES WHERE TABLE_SCHEMA = ?";
    private static final List<String> names = new ArrayList<>();
    private final Set<String> tableNames = new HashSet<>();
    private final EntityManager entityManager;
    private final String schema;

    @Autowired
    private MemberRepository memberRepository;

    public RdbInitializationConfiguration(
        @Value("${schema}")
        String schema,
        EntityManager entityManager
    ) {
        this.schema = schema;
        this.entityManager = entityManager;
    }

    @PostConstruct
    public void afterPropertiesSet() {
        Query query = entityManager.createNativeQuery(ALL_TABLE_NAMES);
        query.setParameter(1, schema);

        List<Object> tables = query.getResultList();
        for (Object obj : tables) {
            tableNames.add(obj.toString());
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void truncateAllEntity() {
        entityManager.flush();
        entityManager.clear();

        entityManager.createNativeQuery(SET_FOREIGN_KEY_CHECKS_FALSE).executeUpdate();

        for (String tableName : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
        }

        entityManager.createNativeQuery(SET_FOREIGN_KEY_CHECKS_TRUE).executeUpdate();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertData() {
        List<String> memberNames = getNames();
        for (String name : memberNames) {
            memberRepository.save(new Member(name));
        }
        names.addAll(memberNames);
    }

    private List<String> getNames() {
        FileSystemResource resource = new FileSystemResource("config/names/name.txt");
        List<String> names = new ArrayList<>();
        try {
            File file = resource.getFile();
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while (bufferedReader.readLine() != null) {
                line = bufferedReader.readLine();
                names.add(line);
            }
            return names;
        } catch (Exception e) {
            throw new RuntimeException("File can not read.");
        }
    }
}
