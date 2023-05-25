package ro.tuc.logic;

import ro.tuc.dao.OrderDAO;
import ro.tuc.logic.validators.Validator;
import ro.tuc.model.Order;
import ro.tuc.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * aceasta clasa se ocupa de operatiile corespunzatoare clasei Order
 */

public class OrderLogic {

    private final OrderDAO orderDAO;
    List<Validator<Order>> validators = new ArrayList<Validator<Order>>();
    public OrderLogic() {

        //validators.add(new QuantityValidator());

        orderDAO = new OrderDAO();
    }

    public Order insert(Order b){
        ProductLogic operation = new ProductLogic();
        Product c = operation.findProductById(b.getProductId());
        int aux = c.getQuantity();
        c.setQuantity(aux - b.getProductQuantity());
        operation.update(c,c.getId(),"quantity",c.getQuantity());
        return orderDAO.insert(b);
    }

    public void delete(Order b){

        orderDAO.delete(b);
    }

    public Order update(Order d, int id, String columnName, Object columnValue){

        if(columnName.equals("age"))
        {
            validators.get(1).validate(d);
        }

        return orderDAO.update(d,d.getId(),columnName,columnValue);

    }

    public Order findClientById(int id) {
        Order or = orderDAO.findById(id);
        if (or == null) {
            throw new NoSuchElementException("The order with id =" + id + " was not found!");
        }
        return or;
    }
}
