package aplication;

import db.DB;
import db.DbIntegrityException;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main {
    public static void main(String[] args) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        PreparedStatement pst = null;

        try {
            conn = DB.getConnection();

            st = conn.createStatement();//instancia o objeto st

//Inserção de dados
//            pst = conn.prepareStatement("INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentID) "
//                    + " VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);//prepara a query
//
//            pst.setString(1, "Guilherme");//seta o primeiro parametro
//            pst.setString(2, "gh630@gmail.com");
//            pst.setDate(3, new Date(sdf.parse("12/04/2006").getTime()));
//            pst.setDouble(4, 2300);
//            pst.setInt(5, 4);
//
//            int rowsAffected = pst.executeUpdate();
//            if (rowsAffected > 0) {
//                ResultSet rs1 = pst.getGeneratedKeys();
//                while (rs1.next()) {
//                    int id = rs1.getInt(1);
//                    System.out.println("Done! Id: " + id);
//                }
//            } else {
//                System.out.println("No rows affected!");
//            }
//Busca de todos os dados
            rs = st.executeQuery("SELECT * FROM department");//executa a query (comando sql)
            while (rs.next()) {//enquanto houver um proximo resultado
                System.out.println(rs.getInt("Id") + ", " + rs.getString("Name"));//get e o nome da coluna
            }


//Atualização de dados
            System.out.println("Checking for update...");
            pst = conn.prepareStatement("UPDATE seller " + "SET BaseSalary = BaseSalary + ? " +
                    "WHERE (DepartmentID = ?)");
            pst.setDouble(1, 200.0);
            pst.setInt(2, 2);

            int rowsAffected = pst.executeUpdate();
            System.out.println("Update completed! Rows affected: " + rowsAffected);
            System.out.println("Done!");

//Deletar dados
//            try {
//                pst = conn.prepareStatement("DELETE FROM department WHERE Id = ?");//prepara a query
//                pst.setInt(1, 4);
//                rowsAffected = pst.executeUpdate();
//                System.out.println("Delete completed! Rows affected: " + rowsAffected);
//            } catch (SQLException e) {
//                throw new DbIntegrityException("Integrity violation: " + e.getMessage());
//            } finally {
//                DB.closeStatement(pst);
//            }

//Transação
           try{
               System.out.println("Starting transaction...");
               conn.setAutoCommit(false);//desabilita o auto commit
               int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");

               int x = 1;
               if (x < 2) {
                   throw new SQLException("Fake error");
               }

               int rows2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2");

               conn.commit();//faz o commit da transação

               System.out.println("Rows1: " + rows1);
               System.out.println("Rows2: " + rows2);
           }catch (SQLException e){
               conn.rollback();
                System.out.println("Transaction rolled back! " + e.getMessage());
           }

            System.out.println("Transaction completed!");
        } catch (SQLException e) {
            e.getStackTrace();
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
            DB.closeStatement(pst);
            DB.closeConnection();
        }

    }
}
