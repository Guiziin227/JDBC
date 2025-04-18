package db;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DB {
    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("config.properties"));

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Conectado com sucesso!");

        } catch (Exception e) {
            System.out.println("❌ Erro ao conectar:");
            e.printStackTrace();
        }
    }
}
