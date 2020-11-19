package de.sane.personservice.controller;

import de.sane.personservice.models.Gender;
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
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


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
        final var person = this.createPerson();
        this.personRepository.save(person);

        final var persons = this.personController.all();

        assertEquals(1, persons.size(), "Person count is not 1");
    }

    @Test
    public void whenCallOneOnFilledDatabase_foundPersonShouldMatch() {
        final var createdPerson = this.createPerson();
        final var dbPerson = this.personRepository.save(createdPerson);

        final var foundPerson = this.personController.one(dbPerson.getId());

        assertEquals(dbPerson, foundPerson, "Person from database doesn't match the returned person from controller");
    }

    @Test
    public void whenCallOneOnNotPresentIdInDatabase_anExceptionShouldBeRaised() {
        final var createdPerson = this.createPerson();
        final var dbPerson = this.personRepository.save(createdPerson);

        assertThrows(ResponseStatusException.class, () -> this.personController.one(dbPerson.getId() + 1));
    }

    @Test
    public void whenAddingPersonToRepository_idOfPersonShouldBeSet() {
        final var createdPerson = this.createPerson();
        final var insertedPerson = this.personController.addPerson(createdPerson);

        assertNotNull(insertedPerson.getId(), "Id of new person was not set");
    }

    @Test
    public void whenAddingPersonToRepository_personShouldBeFoundInDatabase() {
        final var createdPerson = this.createPerson();
        final var insertedPerson = this.personController.addPerson(createdPerson);

        final var hasInsertedPerson = this.personRepository.findAll().stream().anyMatch(person -> Objects.equals(person, insertedPerson));

        assertTrue(hasInsertedPerson, "Person was not found");
    }

    @Test
    public void whenUpdatingPersonFromDatabase_personInDatabaseShouldMatchUpdatePerson() {
        final var createdPerson = this.createPerson();
        final var databasePerson = this.personRepository.save(createdPerson);

        final var updatePerson = this.createPerson();
        updatePerson.setBirthday(LocalDate.of(2004, Month.APRIL, 1));
        updatePerson.setFirstName("Erika");
        updatePerson.setWeight(55f);
        updatePerson.setSize(160);

        final var updatedPerson = this.personController.updatePerson(updatePerson, databasePerson.getId());

        final var hasUpdatedPerson = this.personRepository.findAll().stream().anyMatch(person -> Objects.equals(person, updatedPerson));

        assertTrue(hasUpdatedPerson, "No updated person was found in database");
    }

    @Test
    public void whenUpdatingPersonNotInDatabase_exceptionShouldBeRaised() {
        final var createdPerson = this.createPerson();

        assertThrows(ResponseStatusException.class, () -> this.personController.updatePerson(createdPerson, 1));
    }

    @Test
    public void whenDeletingPersonById_personShouldNotBeFoundInDatabase() {
        final var createdPerson = this.createPerson();
        final var insertedPerson = this.personRepository.save(createdPerson);

        this.personController.deletePerson(insertedPerson.getId());
        final var hasDeletedPerson = this.personRepository.findAll().stream().anyMatch(person -> Objects.equals(person, insertedPerson));

        assertFalse(hasDeletedPerson, "Deleted person was found in database");
    }

    @Test
    public void whenDeletingPersonNotInDatabase_exceptionShouldBeRaised() {
        assertThrows(ResponseStatusException.class, () -> this.personController.deletePerson(1));
    }

    private Person createPerson() {
        return Person.create("Max", "Mustermann")
                .birthday(LocalDate.of(2002, Month.FEBRUARY, 28))
                .gender(Gender.Male)
                .size(180)
                .weight(80.6f).build();
    }

}
