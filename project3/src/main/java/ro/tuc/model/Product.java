package ro.tuc.model;

/**
 * aceasta clasa defineste tipul de date Product
 */

public class Product {
    private int id;
    private String name;
    private int quantity;

    public Product(){
        super();
    }

    public Product(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public Product(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    public int decrementQuantity(int quantity)
    {
        if(this.quantity >= quantity)
        {
            this.quantity = this.quantity - quantity;
            return this.quantity;
        }
        else
            return -1;

    }
}
