/**
 *
 */
package it.cambi.verizon;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import it.cambi.verizon.mongo.repository.AttendeeRepository;
import it.cambi.verizon.service.MeetingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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
public class ApplicationConfiguration {
    private static final int MONGO_PORT = 27017;

    public GenericContainer<?> mongo = new GenericContainer<>("mongo:" + Version.Main.PRODUCTION.asInDownloadPath())
            .withExposedPorts(MONGO_PORT);

    @Bean
    @Profile({"production"})
    public MongoTemplate mongoTemplateProd() throws Exception {
        String ip = "localhost";
        IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
                .net(new Net(ip, MONGO_PORT, Network.localhostIsIPv6())).build();

        MongodStarter starter = MongodStarter.getDefaultInstance();
        MongodExecutable mongodExecutable = starter.prepare(mongodConfig);
        mongodExecutable.start();
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

        return new MongoTemplate(mongoClient, "appointements");
    }


    @Bean
    @Profile({"test"})
    public MongoTemplate mongoTemplate() throws Exception {

        mongo.start();
        MongoClient mongoClient = MongoClients.create("mongodb://" + mongo.getContainerIpAddress() + ":" + mongo.getMappedPort(MONGO_PORT));

        return new MongoTemplate(mongoClient, "appointments");
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
