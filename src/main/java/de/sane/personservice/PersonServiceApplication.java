package de.sane.personservice;

import de.sane.personservice.models.Person;
import de.sane.personservice.repositories.PersonRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.stream.Stream;

@SpringBootApplication
public class PersonServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonServiceApplication.class, args);
    }

//    @Bean
//    public Flyway flyway(DataSource dataSource) {
//        final var flyway = Flyway.configure().dataSource(dataSource).load();
//        flyway.migrate();
//        return flyway;
//    }

}
