/**
 * 
 */
package it.cambi.verizon;

import org.junit.ClassRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.testcontainers.containers.GenericContainer;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import it.cambi.verizon.mongo.repository.AttendeeRepository;

/**
 * @author luca
 *
 */
@Configuration
@EnableMongoRepositories(basePackageClasses = { AttendeeRepository.class })
@Profile({ "test" })
public class ApplicationConfigurationTest
{
    private static final int MONGO_PORT = 27017;

    /**
     * MongoDB
     */
    @ClassRule
    public static GenericContainer<?> mongo = new GenericContainer<>("mongo:4.0.2")
            .withExposedPorts(MONGO_PORT);

    @Bean
    public MongoTemplate mongoTemplate() throws Exception
    {

        mongo.start();
        MongoClient mongoClient = MongoClients.create("mongodb://" + mongo.getContainerIpAddress() + ":" + mongo.getMappedPort(MONGO_PORT) + "");

        return new MongoTemplate(mongoClient, "appointments");
    }

}
