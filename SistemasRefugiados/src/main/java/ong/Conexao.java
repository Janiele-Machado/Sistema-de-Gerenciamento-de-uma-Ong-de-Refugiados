
package ong;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Classe responsável por gerenciar a conexão com o banco de dados.
 * Esta classe fornece um método para estabelecer uma conexão com o banco
 * de dados utilizando JDBC.
 * 
 * @author Samuel
 * @since 16/11/2024
 */
public class Conexao {
    
    /**
     * URL do banco de dados onde a conexão será estabelecida.
     * Aqui está configurado para o banco "banco_refugiados" no MySQL rodando localmente.
     */
    public static final String SERVIDOR = "jdbc:mysql://localhost:3306/banco_refugiados";
    
    /**
     * Usuário para se conectar ao banco de dados.
     */
    public static final String USUARIO = "root";
    
    /**
     * Senha associada ao usuário para se conectar ao banco de dados.
     */
    public static final String SENHA = "******";
    
    /**
     * Estabelece uma conexão com o banco de dados MySQL.
     * 
     * Se a conexão for bem-sucedida, imprime uma mensagem de sucesso.
     * Caso contrário, exibe o erro ocorrido ao tentar estabelecer a conexão.
     * 
     * @return Retorna a conexão com o banco de dados, ou null se ocorrer erro.
     * @throws Exception Se ocorrer um erro ao tentar estabelecer a conexão
     *                     com o banco de dados.
     */
    public Connection getConexao()throws SQLException {
        try {
            Connection conexao = DriverManager.getConnection(SERVIDOR, USUARIO, SENHA);
            if (conexao != null) {
                System.out.println("Conexão bem-sucedida com o banco de dados!");
            }
            return conexao;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            throw e;  // Relança a exceção para que ela possa ser tratada externamente
        }
        
    }
}
    
    

