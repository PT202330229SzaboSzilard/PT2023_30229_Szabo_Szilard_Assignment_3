package ro.tuc.model;

/**
 * aceasta clasa defineste tipul de date Order
 */


public class Order {

    private int id;
    private int clientId;
    private int productId;
    private int productQuantity;

    public Order(){
        super();
    }

    public Order(int clientId, int productId, int productQuantity) {
        this.clientId = clientId;
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public Order(int id, int clientId, int productId, int productQuantity) {
        this.id = id;
        this.clientId = clientId;
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public int getId() {
        return id;
    }

    public int getClientId() {
        return clientId;
    }

    public int getProductId() {
        return productId;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", ClientId=" + clientId +
                ", ProductId=" + productId +
                ", ProductQuantity=" + productQuantity +
                '}';
    }


}
