package de.sane.personservice.controller;

import de.sane.personservice.models.Person;
import de.sane.personservice.repositories.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PersonControllerTest {

    @Autowired
    private PersonController personController;

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void whenCallAllOnEmptyDatabase_noPersonShouldBeReturned() {
        final var persons = this.personController.all();

        assertEquals(0, persons.size());
    }

    @Test
    public void whenCallAllOnFilledDatabase_countOfPersonsShouldMatch() {
        final var person = Person.create("Max", "Mustermann").build();
        this.personRepository.save(person);

        final var persons = this.personController.all();

        assertEquals(1, persons.size());
    }

    @Test
    public void whenCallOnOnFilledDatabase_foundPersonShouldMatch() {
        final var createdPerson = Person.create("Max", "Mustermann").build();
        final var dbPerson = this.personRepository.save(createdPerson);

        final var foundPerson = this.personController.one(dbPerson.getId());

        assertEquals(dbPerson, foundPerson);
    }

}
