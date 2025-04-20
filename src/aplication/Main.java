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
                    + " VALUES (?,?,?,?,?)");//prepara a query

            pst.setString(1, "Guilherme");//seta o primeiro parametro
            pst.setString(2, "gh630@gmail.com");//seta o segundo parametro
            pst.setDate(3, new Date(sdf.parse("12/04/2006").getTime()));//seta o terceiro parametro
            pst.setDouble(4, 2300);//seta o quarto parametro
            pst.setInt(5, 4);//seta o quinto parametro

            int rowsAffected = pst.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);//imprime o numero de linhas afetadas

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
