/** */
package it.cambi.verizon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.util.SocketUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClients;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import it.cambi.verizon.mongo.repository.MeetingRepository;
import it.cambi.verizon.service.MeetingService;

/** @author luca */
@Configuration
@EnableMongoRepositories(basePackageClasses = { MeetingRepository.class })
@ComponentScan(basePackageClasses = { MeetingService.class })
public class ApplicationConfiguration {
    private MongodExecutable mongodExecutable;
    private static final String CONNECTION_STRING = "mongodb://%s:%d";

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        String ip = "localhost";
        int randomPort = SocketUtils.findAvailableTcpPort();

        ImmutableMongodConfig mongodbConfig = MongodConfig.builder()
          .version(Version.Main.PRODUCTION)
          .net(new Net(ip, randomPort, Network.localhostIsIPv6()))
          .build();

        MongodStarter starter = MongodStarter.getDefaultInstance();
        mongodExecutable = starter.prepare(mongodbConfig);
        mongodExecutable.start();
        return new MongoTemplate(MongoClients.create(String.format(CONNECTION_STRING, ip, randomPort)), "appointment");
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
