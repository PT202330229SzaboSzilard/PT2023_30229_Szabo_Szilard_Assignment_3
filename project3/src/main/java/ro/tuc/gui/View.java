package ro.tuc.gui;

import ro.tuc.logic.ClientLogic;
import ro.tuc.logic.OrderLogic;
import ro.tuc.logic.ProductLogic;
import ro.tuc.model.Client;
import ro.tuc.model.Order;
import ro.tuc.model.Product;
import ro.tuc.start.Reflection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static ro.tuc.start.Reflection.getTableHeaders;

/**
 * Interfata grafica a programului pentru ca utilizatorul sa nu aiba de-a face cu backend-ul
 */

public class View {
    private JFrame frame;
    private JTable clientTable;
    private JTable productTable;
    private DefaultTableModel clientTableModel;
    private DefaultTableModel productTableModel;
    private ClientLogic clientLogic = new ClientLogic();
    private ProductLogic productLogic = new ProductLogic();
    private OrderLogic orderLogic = new OrderLogic();
    private List<Client> clientList = clientLogic.findAll();
    private List<Product> productList = productLogic.findAll();

    JComboBox<String> clientComboBox = new JComboBox<>();
    JComboBox<String> productComboBox = new JComboBox<>();

    public View() {

        // Create the main frame
        frame = new JFrame("Product Order System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new GridLayout(3, 1));

        productComboBox.removeAllItems();

        for (Product product : productList) {
            productComboBox.addItem(product.getId() + " " + product.getName());
        }

        clientComboBox.removeAllItems();

        for (Client client : clientList) {
            clientComboBox.addItem(client.getId() + " " + client.getName());
        }

        // Create client operations window
        createClientOperationsWindow();

        // Create product operations window
        createProductOperationsWindow();

        // Create product orders window
        createProductOrdersWindow();

        frame.setVisible(true);
    }

    private void createClientOperationsWindow() {
        JPanel clientPanel = new JPanel();
        clientPanel.setLayout(new BorderLayout());

        clientTableModel = new DefaultTableModel(getTableHeaders(Client.class), 0);
        clientTable = new JTable(clientTableModel);
        JScrollPane clientScrollPane = new JScrollPane(clientTable);

        JButton addClientButton = new JButton("Add Client");
        JButton editClientButton = new JButton("Edit Client");
        JButton deleteClientButton = new JButton("Delete Client");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addClientButton);
        buttonPanel.add(editClientButton);
        buttonPanel.add(deleteClientButton);

        clientPanel.add(clientScrollPane, BorderLayout.CENTER);
        clientPanel.add(buttonPanel, BorderLayout.SOUTH);

        for(Object object: clientList) {
            Object[] rowData = new Object[6];
            Reflection.retrieveProperties(object, rowData);
            clientTableModel.addRow(rowData);
        }

        addClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(frame, "Enter client name:");
                String email = JOptionPane.showInputDialog(frame, "Enter client email:");
                String address = JOptionPane.showInputDialog(frame, "Enter client address:");
                String ageS = JOptionPane.showInputDialog(frame, "Enter client age:");
                int age;
                age = Integer.parseInt(ageS);
                Client cli = new Client(name, email,address,age);
                clientLogic.insert(cli);
                clientTableModel.setRowCount(0);
                clientList = clientLogic.findAll();
                for(Object object: clientList) {
                    Object[] rowData = new Object[6];
                    Reflection.retrieveProperties(object, rowData);
                    clientTableModel.addRow(rowData);
                }
                clientComboBox.removeAllItems();

                for (Client client : clientList) {
                    clientComboBox.addItem(client.getId() + " " + client.getName());
                }

            }
        });

        editClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = clientTable.getSelectedRow();
                if (selectedRow != -1) {
                    String name = JOptionPane.showInputDialog(frame, "Enter client name:",
                            clientTableModel.getValueAt(selectedRow, 1));
                    String email = JOptionPane.showInputDialog(frame, "Enter client email:",
                            clientTableModel.getValueAt(selectedRow, 2));
                    String address = JOptionPane.showInputDialog(frame, "Enter client address:",
                            clientTableModel.getValueAt(selectedRow, 3));
                    String ageS = JOptionPane.showInputDialog(frame, "Enter client age:",
                            clientTableModel.getValueAt(selectedRow, 4));
                    int age;
                    age = Integer.parseInt(ageS);
                    Client c = clientLogic.findClientById((Integer) clientTableModel.getValueAt(selectedRow, 0));
                    if(!name.equals(c.getName()))
                    {
                        clientLogic.update(c,c.getId(),"name",name);
                    }
                    if(!email.equals(c.getEmail()))
                    {
                        clientLogic.update(c,c.getId(),"email",email);
                    }
                    if(!address.equals(c.getAddress()))
                    {
                        clientLogic.update(c,c.getId(),"address",address);
                    }
                    if(c.getAge() != age)
                    {
                        clientLogic.update(c,c.getId(),"age",age);
                    }
                    clientList = clientLogic.findAll();
                    clientTableModel.setRowCount(0);
                    for(Object object: clientList) {
                        Object[] rowData = new Object[6];
                        Reflection.retrieveProperties(object, rowData);
                        clientTableModel.addRow(rowData);
                    }

                    clientComboBox.removeAllItems();

                    for (Client client : clientList) {
                        clientComboBox.addItem(client.getId() + " " + client.getName());
                    }
                }
            }
        });

        deleteClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = clientTable.getSelectedRow();
                if (selectedRow != -1) {
                    Client c = clientLogic.findClientById((Integer) clientTableModel.getValueAt(selectedRow, 0));
                    clientLogic.delete(c);
                    clientList = clientLogic.findAll();
                    clientTableModel.setRowCount(0);
                    for(Object object: clientList) {
                        Object[] rowData = new Object[6];
                        Reflection.retrieveProperties(object, rowData);
                        clientTableModel.addRow(rowData);
                    }

                    clientComboBox.removeAllItems();

                    for (Client client : clientList) {
                        clientComboBox.addItem(client.getId() + " " + client.getName());
                    }
                }
            }
        });

        frame.add(clientPanel);
    }

    private void createProductOperationsWindow() {
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new BorderLayout());

        productTableModel = new DefaultTableModel(getTableHeaders(Product.class), 0);
        productTable = new JTable(productTableModel);
        JScrollPane productScrollPane = new JScrollPane(productTable);

        JButton addProductButton = new JButton("Add Product");
        JButton editProductButton = new JButton("Edit Product");
        JButton deleteProductButton = new JButton("Delete Product");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addProductButton);
        buttonPanel.add(editProductButton);
        buttonPanel.add(deleteProductButton);

        productPanel.add(productScrollPane, BorderLayout.CENTER);
        productPanel.add(buttonPanel, BorderLayout.SOUTH);

        for(Object object: productList) {
            Object[] rowData = new Object[4];
            Reflection.retrieveProperties(object, rowData);
            productTableModel.addRow(rowData);
        }

        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name = JOptionPane.showInputDialog(frame, "Enter product name:");
                int quantity = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter product quantity:"));
                Product pro = new Product(name, quantity);
                productLogic.insert(pro);
                productTableModel.setRowCount(0);
                productList = productLogic.findAll();
                for(Object object: productList) {
                    Object[] rowData = new Object[4];
                    Reflection.retrieveProperties(object, rowData);
                    productTableModel.addRow(rowData);
                }

                productComboBox.removeAllItems();

                for (Product product : productList) {
                    productComboBox.addItem(product.getId() + " " + product.getName());
                }
            }
        });

        editProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {
                    String name = JOptionPane.showInputDialog(frame, "Enter product name:",
                            productTableModel.getValueAt(selectedRow, 1));
                    int quantity = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter product quantity:",
                            productTableModel.getValueAt(selectedRow, 2)));
                    Product c = productLogic.findProductById((Integer) productTableModel.getValueAt(selectedRow, 0));
                    if(!name.equals(c.getName()))
                    {
                        productLogic.update(c,c.getId(),"name",name);
                    }
                    if(c.getQuantity() != quantity)
                    {
                        productLogic.update(c,c.getId(),"quantity",quantity);
                    }

                    productList = productLogic.findAll();
                    productTableModel.setRowCount(0);
                    for(Object object:productList) {
                        Object[] rowData = new Object[6];
                        Reflection.retrieveProperties(object, rowData);
                        productTableModel.addRow(rowData);
                    }

                    productComboBox.removeAllItems();

                    for (Product product : productList) {
                        productComboBox.addItem(product.getId() + " " + product.getName());
                    }
                }
            }
        });

        deleteProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {
                    Product c = productLogic.findProductById((Integer) productTableModel.getValueAt(selectedRow, 0));
                    productLogic.delete(c);
                    productList = productLogic.findAll();
                    productTableModel.setRowCount(0);
                    for(Object object:productList) {
                        Object[] rowData = new Object[6];
                        Reflection.retrieveProperties(object, rowData);
                        productTableModel.addRow(rowData);
                    }

                    productComboBox.removeAllItems();

                    for (Product product : productList) {
                        productComboBox.addItem(product.getId() + " " + product.getName());
                    }
                }
            }
        });

        frame.add(productPanel);
    }

    private void createProductOrdersWindow() {
        JPanel orderPanel = new JPanel();
        orderPanel.setLayout(new BorderLayout());

        JLabel clientLabel = new JLabel("Select Client:");

        JLabel productLabel = new JLabel("Select Product:");

        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityTextField = new JTextField();
        JButton createOrderButton = new JButton("Create Order");

        JPanel formPanel = new JPanel(new GridLayout(4, 2));
        formPanel.add(clientLabel);
        formPanel.add(clientComboBox);
        formPanel.add(productLabel);
        formPanel.add(productComboBox);
        formPanel.add(quantityLabel);
        formPanel.add(quantityTextField);
        formPanel.add(createOrderButton);

        orderPanel.add(formPanel, BorderLayout.CENTER);


        createOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String clientString = (String) clientComboBox.getSelectedItem();
                String numericString = clientString.replaceAll("[^\\d-]","");
                int clientIndex = Integer.parseInt(numericString);
                String productString = (String) productComboBox.getSelectedItem();
                String numericString2 = productString.replaceAll("[^\\d-]","");
                int productIndex = Integer.parseInt(numericString2);
                int quantity = Integer.parseInt(quantityTextField.getText());

                if (clientIndex == -1 || productIndex == -1) {
                    JOptionPane.showMessageDialog(frame, "Please select a client and a product.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else if (quantity <= 0) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid quantity.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Client client = clientLogic.findClientById(clientIndex);
                    Product product = productLogic.findProductById(productIndex);
                    if (product.getQuantity() < quantity) {
                        JOptionPane.showMessageDialog(frame, "Not enough products in stock.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        Order order = new Order(client.getId(), product.getId(), quantity);
                        orderLogic.insert(order);
                        productTableModel.setRowCount(0);
                        productList = productLogic.findAll();

                        for(Object object: productList) {
                            Object[] rowData = new Object[4];
                            Reflection.retrieveProperties(object, rowData);
                            productTableModel.addRow(rowData);
                        }

                        JOptionPane.showMessageDialog(frame, "Order created successfully.",
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        frame.add(orderPanel);
    }
}