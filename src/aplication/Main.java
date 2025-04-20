package aplication;

import db.DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {

        Connection conn = null;

        Statement st = null;
        ResultSet rs = null;

        try {
            conn = DB.getConnection();

            st = conn.createStatement();//instancia o objeto st

            rs = st.executeQuery("SELECT * FROM department");//executa a query (comando sql)

            while (rs.next()) {//enquanto houver um proximo resultado
                System.out.println(rs.getInt("Id") + ", " + rs.getString("Name"));//get e o nome da coluna
            }

        } catch (SQLException e) {
            e.getStackTrace();
        }
    }
}
