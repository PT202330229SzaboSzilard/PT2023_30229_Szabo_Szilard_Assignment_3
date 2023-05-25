package ro.tuc.dao;

import ro.tuc.model.Client;

import java.util.List;

/**
 * ClientDAO implementeaza metodele findById, insert, delete, update, findAll pentru clasa Client
 */

public class ClientDAO extends AbstractDAO<Client> {

    public Client findById(int id) {
        return super.findById(id);
    }

    public Client insert(Client a){
        return super.insert(a);
    }

    public void delete(Client b){
        super.delete(b.getId());
    }

    public Client update(Client c, int id, String columnName, Object columnValue){
        return super.update(c,c.getId(),columnName,columnValue);
    }

    @Override
    public List<Client> findAll() {
        return super.findAll();
    }
}
