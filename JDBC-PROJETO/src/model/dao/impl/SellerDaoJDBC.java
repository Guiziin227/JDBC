package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

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
        PreparedStatement stmt = null; //preparando a consulta
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement("SELECT seller.*,department.Name as DepName " +
                    "FROM seller INNER JOIN department " +
                    "ON seller.DepartmentId = department.Id " +
                    "WHERE seller.Id = ?"); // fazendo a consulta

            stmt.setInt(1, id); // setando o id do vendedor
            rs = stmt.executeQuery(); // executando a consulta

            if (rs.next()) {
                Department department = new Department();
                department.setId(rs.getInt("DepartmentId"));
                department.setName(rs.getString("DepName"));
                Seller seller = new Seller();
                seller.setId(rs.getInt("Id"));
                seller.setName(rs.getString("Name"));
                seller.setEmail(rs.getString("Email"));
                seller.setBirthDate(rs.getDate("BirthDate"));
                seller.setBaseSalary(rs.getDouble("BaseSalary"));
                seller.setDepartmentId(department);
                return seller; // retornando o vendedor
            }
            return null;

        } catch (SQLException e){
            throw new DbException("Error preparing statement", e);
        } finally {
            DB.closeStatement(stmt); // fechando a conexão
            DB.closeResultSet(rs); // fechando o result set
        }
    }

    @Override
    public List<Seller> findAll() {
        // Implementation for finding all sellers in the database
        return null;
    }


}
