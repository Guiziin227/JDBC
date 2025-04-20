package aplication;

import db.DB;

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


            pst = conn.prepareStatement("INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentID) "
                    + " VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);//prepara a query

            pst.setString(1, "Guilherme");//seta o primeiro parametro
            pst.setString(2, "gh630@gmail.com");//seta o segundo parametro
            pst.setDate(3, new Date(sdf.parse("12/04/2006").getTime()));//seta o terceiro parametro
            pst.setDouble(4, 2300);//seta o quarto parametro
            pst.setInt(5, 4);//seta o quinto parametro

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs1 = pst.getGeneratedKeys();
                while (rs1.next()) {
                    int id = rs1.getInt(1);
                    System.out.println("Done! Id: " + id);
                }
            } else {
                System.out.println("No rows affected!");
            }

            rs = st.executeQuery("SELECT * FROM department");//executa a query (comando sql)
            while (rs.next()) {//enquanto houver um proximo resultado
                System.out.println(rs.getInt("Id") + ", " + rs.getString("Name"));//get e o nome da coluna
            }

        } catch (SQLException | ParseException e) {
            e.getStackTrace();
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
            DB.closeStatement(pst);
            DB.closeConnection();
        }

    }
}
