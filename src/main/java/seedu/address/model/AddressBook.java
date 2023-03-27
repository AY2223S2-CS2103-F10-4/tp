package seedu.address.model;

import static java.util.Objects.requireNonNull;


import java.util.*;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    /**
     * Sets current list to be sorted list of its current data
     * Sorting is done by comparing business sizes, larger business sizes near the top
     */
    public void sortPersonsDescending() {
        List<Person> sortedOldList = this.getPersonList().sorted();
        this.setPersons(sortedOldList);
    }

    /**
     * Sets current list to be sorted list of its current data
     * Sorting is done by comparing business sizes, larger business sizes near the bottom
     */
    public void sortPersonsAscending() {
        List<Person> sortedOldList = this.getPersonList()
                .sorted(Comparator.comparing(Person::getBusinessSizeInt));
        this.setPersons(sortedOldList);
    }

    /**
     * Sets current list to be sorted list of its current data
     * Sorting is done by comparing Names in alphabetical order
     */
    public void sortPersonsName() {
        List<Person> sortedOldList = this.getPersonList()
                .sorted(Comparator.comparing(Person::getNameString));
        this.setPersons(sortedOldList);
    }



    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    /**
     * Return the size of the list.
     *
     */
    @Override
    public int size() {
        return persons.size();
    }

    // function to sum the total value of all the business size in the people

    /**
     * Gets the sum of the user's potential earnings.
     *
     * @return The sum of the user's potential earnings.
     */
    @Override
    public int getPotentialEarnings() {
        Iterator<Person> iterator = persons.iterator();
        int totalValue = 0;
        while (iterator.hasNext()) {
            totalValue += iterator.next().getBusinessSize().getNumericValue();
        }
        return totalValue;
    }

    /**
     * Returns the a string containing all tags.
     */
    @Override
    public String getTags() {
        Iterator<Person> personIterator = persons.iterator();
        Set<Tag> tags = new HashSet<>();
        String tagsInString = "";
        while (personIterator.hasNext()) {
            tags.addAll(personIterator.next().getTags());
        }
        Iterator<Tag> tagIterator = tags.iterator();
        while (tagIterator.hasNext()) {
            Tag temp = tagIterator.next();
            if (tagIterator.hasNext()) {
                tagsInString += temp + ", ";
            } else {
                tagsInString += temp;
            }
        }
        return tagsInString;
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
