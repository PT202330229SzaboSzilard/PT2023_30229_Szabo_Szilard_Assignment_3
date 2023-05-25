package ro.tuc.logic.validators;

/**
 * este interfata pe care toate restul validatorelor o implementeaza
 * @param <T>
 */

public interface Validator<T> {

    public void validate(T t);
    public void validateO(Object o);
}

