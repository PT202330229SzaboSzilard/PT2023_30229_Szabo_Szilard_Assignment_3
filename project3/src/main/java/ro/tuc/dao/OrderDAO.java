package ro.tuc.dao;

import ro.tuc.model.Order;

/**
 * OrderDAO implementeaza metodele findById, insert, delete, update pentru clasa Order
 */
public class OrderDAO extends AbstractDAO<Order> {

    public Order findById(int id) {
        return super.findById(id);
    }

    public Order insert(Order a){
        return super.insert(a);
    }

    public void delete(Order b){
        super.delete(b.getId());
    }

    public Order update(Order c, int id, String columnName, Object columnValue){
        return super.update(c,c.getId(),columnName,columnValue);
    }
}
