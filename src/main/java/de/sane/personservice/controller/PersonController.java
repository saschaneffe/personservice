package de.sane.personservice.controller;

import de.sane.personservice.models.Person;
import de.sane.personservice.repositories.PersonRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping
    public List<Person> all() {
        return this.personRepository.findAll();
    }

    @GetMapping("/{id}")
    public Person one(@PathVariable Integer id) {
        return this.personRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person was not found"));
    }

    @PostMapping
    public Person addPerson(@Valid @RequestBody Person person) {
        return this.personRepository.save(person);
    }

    @PutMapping("/{id}")
    public Person updatePerson(@Valid @RequestBody Person person, @PathVariable Integer id) {
        return this.personRepository.findById(id).map(dbPerson -> {
            dbPerson.setFirstName(person.getFirstName());
            dbPerson.setLastName(person.getLastName());
            dbPerson.setGender(person.getGender());
            dbPerson.setBirthday(person.getBirthday());
            dbPerson.setSize(person.getSize());
            dbPerson.setWeight(person.getWeight());
            return this.personRepository.save(dbPerson);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person was not found"));
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Integer id) {
        final var personExists = this.personRepository.existsById(id);
        if(!personExists) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Person was not found"
            );
        }

        this.personRepository.deleteById(id);
    }

}
