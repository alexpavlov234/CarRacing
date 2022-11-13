package hristov.mihail.carracing;

import hristov.mihail.carracing.models.Person;
import hristov.mihail.carracing.services.*;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args){
        Person person = new Person(5,"Misho", "Benkoveca", "Skisho", 18,"bulgarin", 100, null);

      // PersonService.addPerson(person);
        //PersonService.addPerson("Saki", null);
PersonService.updatePerson(person);
ArrayList<Person> people = PersonService.getAllPerson();
        System.out.println(people);
        System.out.println(PersonService.getLastPerson().getFirstNamePerson());
        System.out.println(PersonService.getPerson(2).getFirstNamePerson());
        //PersonService.deletePerson();
    }
}
