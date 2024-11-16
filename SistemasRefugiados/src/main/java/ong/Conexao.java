
package ong;
import java.sql.Connection;
import java.sql.DriverManager;


public class Conexao {
    public static final String SERVIDOR = "jdbc:mysql://localhost:3306/banco_refugiados";
    public static final String USUARIO = "root";
    public static final String SENHA = "....";
    
    
    public Connection getConexao() {
        try {
            Connection conexao = DriverManager.getConnection(SERVIDOR, USUARIO, SENHA);
            if (conexao != null) {
                System.out.println("Conexão bem-sucedida com o banco de dados!");
            }
            return conexao;
        } catch (Exception e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
        }
        return null;
    }
}
    
    

