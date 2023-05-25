package ro.tuc.logic.validators;

import ro.tuc.model.Client;

/**
 * e un validator pentru varsta clientului, acesta trebuie sa aiba intre 15 si 60 ani
 */

public class AgeValidator implements Validator<Client> {

    private static final int MIN_AGE = 15;
    private static final int MAX_AGE = 60;
    @Override
    public void validate(Client client) {

        if (client.getAge() < MIN_AGE || client.getAge() > MAX_AGE) {
            throw new IllegalArgumentException("The Client Age limit is not respected!");
        }
    }

    @Override
    public void validateO(Object o) {

        Integer s = (Integer) o;
        if (s < MIN_AGE || s > MAX_AGE) {
            throw new IllegalArgumentException("The Student Age limit is not respected!");
        }
    }

}

