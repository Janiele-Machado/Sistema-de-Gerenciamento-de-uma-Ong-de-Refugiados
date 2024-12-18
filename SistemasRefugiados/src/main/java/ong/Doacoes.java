package ong;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 * Classe que gerencia as operações relacionadas às doações, incluindo inserção
 * e listagem das doações de acordo com o doador.
 *
 * @author Samuel
 * @since 16/11/2024
 */
public class Doacoes {

    private String doacoes_tipo;
    private String doacoes_quant;
    private String doacoes_date;
    private int doadorid;
    
    
    /**
    * Construtor para inicializar os detalhes de uma doação.
    * 
    * @param doacoes_tipo O tipo de doação (ex: alimentos, roupas, etc.)
    * @param doacoes_quant A quantidade da doação
    * @param doacoes_date A data da doação
    * @param doadorid O ID do doador que está fazendo a doação
    */
    public Doacoes(String doacoes_tipo, String doacoes_quant, String doacoes_date, int doadorid) {
        this.doacoes_tipo = doacoes_tipo;
        this.doacoes_quant = doacoes_quant;
        this.doacoes_date = doacoes_date;
        this.doadorid = doadorid;

    }

    public void setDoadorid(int dd_id) {
        doadorid = dd_id;

    }
    
    /**
    * Insere uma nova doação no banco de dados.
    * Realiza uma transação para garantir a consistência dos dados. 
    * Se a inserção for bem-sucedida, confirma a transação. Caso contrário, desfaz a transação.
    * 
    * @throws SQLException Se ocorrer um erro ao conectar ao banco de dados ou ao executar a consulta.
    */
    public void inserir() {
        Connection conexao = new Conexao().getConexao();
        String sqldoacao = "INSERT INTO doacoes (doacoes_tipo, doacoes_quant ,doacoes_date, fk_doadores_doadores_id) VALUES(?,?,?,?)";

        try {
            // Começar uma transação para garantir consistência
            conexao.setAutoCommit(false);
            PreparedStatement comandoDoacoes = conexao.prepareStatement(sqldoacao);
            comandoDoacoes.setString(1, this.doacoes_tipo);
            comandoDoacoes.setString(2, this.doacoes_quant);
            comandoDoacoes.setString(3, this.doacoes_date);
            comandoDoacoes.setInt(4, this.doadorid);
            comandoDoacoes.executeUpdate();

            // Confirmar a transação
            conexao.commit();
            System.out.println("Doador inserido com sucesso!");

            // Fechar as conexões
            comandoDoacoes.close();
            conexao.setAutoCommit(true);
        } catch (Exception e) {
            try {
                // Em caso de erro, desfaz a transação
                conexao.rollback();
            } catch (SQLException rollbackEx) {
                System.out.println("Erro ao reverter a transação: " + rollbackEx.getMessage());
            }
            System.out.println("Erro ao inserir doador: " + e.getMessage());

        }
    }

    public void listar_doadocao() {
        Connection conexao = new Conexao().getConexao();
        String sql_listard = "select *from doadores inner join doacoes on fk_usuarios_doadores_id=fk_doadores_doadores_id;";
        try {
            PreparedStatement comando_list = conexao.prepareStatement(sql_listard);
            ResultSet rsd = comando_list.executeQuery();

            while (rsd.next()) {
                System.out.println("tipo de doacao: " + rsd.getString("doacoes_tipo"));
                System.out.println("quantidade: " + rsd.getString("doacoes_quant"));
                System.out.println("data: " + rsd.getString("doacoes_date"));
                System.out.println("email do doador: " + rsd.getString("doadores_email"));
                System.out.println("id: " + rsd.getInt("fk_doadores_doadores_id"));
            }

            comando_list.close();

        } catch (SQLException e) {
            System.out.println("e");
        }
    }

}
