package ro.tuc.logic.validators;

import ro.tuc.model.Product;

/**
 * acest validator se asigura ca exista produse in stock si ca nu-s mai multe de 100
 */

public class QuantityValidator implements Validator<Product> {

    private static final int MIN_Q = 0;
    private static final int MAX_Q = 100;
    @Override
    public void validate(Product product) {

        if (product.getQuantity() < MIN_Q || product.getQuantity() > MAX_Q) {
            throw new IllegalArgumentException("The Product Quantity limit is not respected!");
        }
    }

    @Override
    public void validateO(Object o) {

        Integer s = (Integer) o;
        if (s < MIN_Q || s > MAX_Q) {
            throw new IllegalArgumentException("The Product Quantity limit is not respected!");
        }
    }
}
