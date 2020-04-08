package it.cambi.verizon;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {VerizonApplication.class})
@ExtendWith(SpringExtension.class)
public class VerizonApplicationDaoTest {

    private @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void test() {
        assertNotNull(mongoTemplate);
    }
}
