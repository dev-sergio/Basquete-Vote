package banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConnectionFactory {

    String server;
    String door;
    String dataBase;
    String user;
    String password;

    public Connection getConnection() throws Exception {
        try {
            server = "127.0.0.1";
            door = "3306";
            dataBase = "votacao";
            user = "root";
            password = "123456";
            

            return DriverManager.getConnection("jdbc:mysql://" + server + ":" + door + "/" + dataBase, user, password);

        } catch (SQLException excecao) {
            
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a conexão!\n\nContate o administrador do sistema!", "ATENÇÃO", 0);
            System.exit(1);
            throw new RuntimeException(excecao);
            
        }
    }
}