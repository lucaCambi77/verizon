/**
 *
 */
package it.cambi.verizon;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import de.flapdoodle.embed.mongo.distribution.Version;
import it.cambi.verizon.mongo.repository.AttendeeRepository;
import it.cambi.verizon.service.MeetingService;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.testcontainers.containers.GenericContainer;

/**
 * @author luca
 *
 */
@Configuration
@EnableMongoRepositories(basePackageClasses = {AttendeeRepository.class})
@ComponentScan(basePackageClasses = {MeetingService.class})
@Import(EmbeddedMongoAutoConfiguration.class)
public class ApplicationConfiguration {
    private static final int MONGO_PORT = 27017;

    public GenericContainer<?> mongoContainer = new GenericContainer<>("mongo:" + Version.Main.PRODUCTION.asInDownloadPath())
            .withExposedPorts(MONGO_PORT);

    @Bean
    @Profile({"test"})
    public MongoTemplate mongoTemplate() throws Exception {

        mongoContainer.start();
        MongoClient mongoClient = MongoClients.create("mongodb://" + mongoContainer.getContainerIpAddress() + ":" + mongoContainer.getMappedPort(MONGO_PORT));

        return new MongoTemplate(mongoClient, "appointments");
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
