package ro.tuc.logic.validators;

import ro.tuc.logic.ProductLogic;
import ro.tuc.model.Order;
import ro.tuc.model.Product;

/**
 * acest validator se asigura ca produsele din stock acopera cererea din comanda
 */

public class OrderQuantityValidator implements Validator<Order>{

    @Override
    public void validate(Order order) {
        Product product = (new ProductLogic()).findProductById(order.getProductId());
        if(order.getProductQuantity() > product.getQuantity())
        {
            order.setId(-1);
            throw new IllegalArgumentException("Not enough products for the order!");
        }
    }

    @Override
    public void validateO(Object o) {

    }
}
