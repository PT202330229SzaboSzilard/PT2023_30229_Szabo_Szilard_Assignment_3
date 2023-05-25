package ro.tuc.dao;

import ro.tuc.model.Product;

import java.util.List;

/**
 * ProductDAO implementeaza metodele findById, insert, delete, update, findAll pentru clasa Product
 */
public class ProductDAO extends AbstractDAO<Product>{
    public Product findById(int id) {
        return super.findById(id);
    }

    public Product insert(Product a){
        return super.insert(a);
    }

    public void delete(Product b){
        super.delete(b.getId());
    }

    public Product update(Product c, String columnName, Object columnValue){
        return super.update(c,c.getId(),columnName,columnValue);
    }

    @Override
    public List<Product> findAll() {
        return super.findAll();
    }
}
