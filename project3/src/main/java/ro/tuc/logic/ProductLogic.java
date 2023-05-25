package ro.tuc.logic;

import ro.tuc.dao.ProductDAO;
import ro.tuc.logic.validators.QuantityValidator;
import ro.tuc.logic.validators.Validator;
import ro.tuc.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * aceasta clasa se ocupa de operatiile corespunzatoare clasei Product
 */

public class ProductLogic {

    private final ProductDAO productDAO;

    List<Validator<Product>> validators = new ArrayList<Validator<Product>>();
    public ProductLogic() {

        validators.add(new QuantityValidator());


        productDAO = new ProductDAO();
    }

    public Product insert(Product b){

        return productDAO.insert(b);
    }

    public void delete(Product b){

        productDAO.delete(b);
    }

    public Product update(Product d, int id, String columnName, Object columnValue){

        if(columnName.equals("quantity"))
        {
            validators.get(0).validate(d);
        }

        return productDAO.update(d,d.getId(),columnName,columnValue);

    }

    public Product findProductById(int id) {
        Product pr = productDAO.findById(id);
        if (pr == null) {
            throw new NoSuchElementException("The product with id =" + id + " was not found!");
        }
        return pr;
    }

    public ArrayList<Product> findAll()
    {
        return (ArrayList<Product>) productDAO.findAll();
    }
}
