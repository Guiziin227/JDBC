package model.dao.impl;

import model.dao.SellerDao;
import model.entities.Seller;

import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    @Override
    public void insert(Seller obj) {
        // Implementation for inserting a seller into the database
    }

    @Override
    public void update(Seller obj) {
        // Implementation for updating a seller in the database
    }

    @Override
    public void deleteById(Integer id) {
        // Implementation for deleting a seller by ID from the database
    }

    @Override
    public Seller findById(Integer id) {
        // Implementation for finding a seller by ID in the database
        return null;
    }

    @Override
    public List<Seller> findAll() {
        // Implementation for finding all sellers in the database
        return null;
    }


}
