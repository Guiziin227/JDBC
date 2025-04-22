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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement stmt = null; //preparando a consulta
        try {
            stmt = conn.prepareStatement("INSERT INTO seller " +
                    "(Name, Email, BirthDate, BaseSalary, DepartmentId) " +
                    "VALUES (?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);

            stmt.setString(1, obj.getName());
            stmt.setString(2, obj.getEmail());
            stmt.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            stmt.setDouble(4, obj.getBaseSalary());
            stmt.setInt(5, obj.getDepartmentId().getId());

            int rowsAffected = stmt.executeUpdate(); // executando a consulta

            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys(); // pegando o id do vendedor
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id); // setando o id do vendedor
                }
                DB.closeResultSet(rs); // fechando o result set
            } else {
                throw new DbException("Unexpected error! No rows affected.");
            }

        } catch (SQLException e) {
            throw new DbException("Error inserting seller", e);
        } finally {
            DB.closeStatement(stmt); // fechando a conexão
        }
    }

    @Override
    public void update(Seller obj) {
        PreparedStatement stmt = null; //preparando a consulta
        try {
            stmt = conn.prepareStatement("UPDATE seller " +
                    "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " +
                    "WHERE Id = ?");

            stmt.setString(1, obj.getName());
            stmt.setString(2, obj.getEmail());
            stmt.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            stmt.setDouble(4, obj.getBaseSalary());
            stmt.setInt(5, obj.getDepartmentId().getId());
            stmt.setInt(6, obj.getId());

            stmt.executeUpdate(); // executando a consulta

        } catch (SQLException e) {
            throw new DbException("Error updating seller", e);
        } finally {
            DB.closeStatement(stmt); // fechando a conexão
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");

            st.setInt(1, id);

            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }


    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement stmt = null; //preparando a consulta
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement("SELECT seller.*,department.Name as DepName " +
                    "FROM seller INNER JOIN department " +
                    "ON seller.DepartmentId = department.Id " +
                    "WHERE DepartmentId = ? " +
                    "ORDER BY Name");

            stmt.setInt(1, department.getId()); // setando o id do departamento

            rs = stmt.executeQuery(); // executando a consulta

            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {
                Department dep = map.get(rs.getInt("DepartmentId"));
            // instanciando o departamento e evitando a repetição de departamento

                if (dep == null) {
                    dep = new Department();
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller seller = instantiateSeller(rs, dep); // instanciando o vendedor
                sellers.add(seller); // adicionando o vendedor a lista
            }
            return sellers;


        } catch (SQLException e) {
            throw new DbException("Error preparing statement", e);
        }
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
                Department department = instantiateDepartment(rs); // instanciando o departamento
                Seller seller = instantiateSeller(rs, department); // instanciando o vendedor
                return seller; // retornando o vendedor
            }
            return null;

        } catch (SQLException e) {
            throw new DbException("Error preparing statement", e);
        } finally {
            DB.closeStatement(stmt); // fechando a conexão
            DB.closeResultSet(rs); // fechando o result set
        }
    }

    private Seller instantiateSeller(ResultSet rs, Department department) throws SQLException {
        Seller seller = new Seller();
        seller.setId(rs.getInt("Id"));
        seller.setName(rs.getString("Name"));
        seller.setEmail(rs.getString("Email"));
        seller.setBirthDate(rs.getDate("BirthDate"));
        seller.setBaseSalary(rs.getDouble("BaseSalary"));
        seller.setDepartmentId(department);

        return seller; // retornando o vendedor
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep; // retornando o departamento
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement stmt = null; //preparando a consulta
        ResultSet rs = null;

        try{
            stmt = conn.prepareStatement("SELECT seller.*,department.Name as DepName\n" +
                    "FROM seller INNER JOIN department\n" +
                    "ON seller.DepartmentId = department.Id\n" +
                    "ORDER BY Name");

            rs = stmt.executeQuery(); // executando a consulta

            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {
                Department dep = map.get(rs.getInt("DepartmentId"));
                // instanciando o departamento e evitando a repetição de departamento

                if (dep == null) {
                    dep = new Department();
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller seller = instantiateSeller(rs, dep); // instanciando o vendedor
                sellers.add(seller); // adicionando o vendedor a lista
            }
            return sellers;
        } catch (SQLException e){
            throw new DbException("Error preparing statement", e);
        }
    }


}
