package ro.tuc.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import ro.tuc.logic.validators.EmailValidator;
import ro.tuc.logic.validators.AgeValidator;
import ro.tuc.logic.validators.Validator;
import ro.tuc.dao.ClientDAO;
import ro.tuc.model.Client;

/**
 * aceasta clasa se ocupa de operatiile corespunzatoare clasei Client
 */

public class ClientLogic {

    private final ClientDAO clientDAO;
    List<Validator<Client>> validators = new ArrayList<Validator<Client>>();
    public ClientLogic() {

        validators.add(new EmailValidator());
        validators.add(new AgeValidator());

        clientDAO = new ClientDAO();
    }

    public Client insert(Client b){
        for(Validator<Client> c: validators)
        {
            c.validate(b);
        }
        return clientDAO.insert(b);
    }

    public void delete(Client b){

        clientDAO.delete(b);
    }

    public Client update(Client d, int id, String columnName, Object columnValue){

        if(columnName.equals("age"))
        {
            validators.get(1).validate(d);
        }
        return clientDAO.update(d,d.getId(),columnName,columnValue);

    }

    public Client findClientById(int id) {
        Client cl = clientDAO.findById(id);
        if (cl == null) {
            throw new NoSuchElementException("The client with id = " + id + " was not found!");
        }
        return cl;
    }

    public ArrayList<Client> findAll()
    {
        return (ArrayList<Client>) clientDAO.findAll();
    }
}


