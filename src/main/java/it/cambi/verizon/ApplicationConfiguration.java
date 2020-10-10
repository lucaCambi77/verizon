/** */
package it.cambi.verizon;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClients;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import it.cambi.verizon.mongo.repository.MeetingRepository;
import it.cambi.verizon.service.MeetingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.util.SocketUtils;

/** @author luca */
@Configuration
@EnableMongoRepositories(basePackageClasses = {MeetingRepository.class})
@ComponentScan(basePackageClasses = {MeetingService.class})
public class ApplicationConfiguration {
  private MongodExecutable mongodExecutable;
  private MongoTemplate mongoTemplate;
  private static final String CONNECTION_STRING = "mongodb://%s:%d";

  @Bean
  public MongoTemplate mongoTemplate() throws Exception {
    String ip = "localhost";
    int randomPort = SocketUtils.findAvailableTcpPort();

    IMongodConfig mongodConfig =
        new MongodConfigBuilder()
            .version(Version.Main.PRODUCTION)
            .net(new Net(ip, randomPort, Network.localhostIsIPv6()))
            .build();

    MongodStarter starter = MongodStarter.getDefaultInstance();
    mongodExecutable = starter.prepare(mongodConfig);
    mongodExecutable.start();
    return
        new MongoTemplate(
            MongoClients.create(String.format(CONNECTION_STRING, ip, randomPort)), "appointment");
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}
