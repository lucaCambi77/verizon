/**
 *
 */
package it.cambi.verizon;

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

/**
 * @author luca
 *
 */
@Configuration
@Profile({ "production" })
@EnableMongoRepositories(basePackageClasses = { AttendeeRepository.class })
@ComponentScan(basePackageClasses = { MeetingService.class })
public class ApplicationConfiguration
{
    @Bean
    public MongoTemplate mongoTemplate() throws Exception
    {
        String ip = "localhost";
        int port = 27017;

        IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
                .net(new Net(ip, port, Network.localhostIsIPv6())).build();

        MongodStarter starter = MongodStarter.getDefaultInstance();
        MongodExecutable mongodExecutable = starter.prepare(mongodConfig);
        mongodExecutable.start();
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

        return new MongoTemplate(mongoClient, "appointements");
    }

}
