package ong;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
     * Insere uma nova doação no banco de dados. Realiza uma transação para
     * garantir a consistência dos dados. Se a inserção for bem-sucedida,
     * confirma a transação. Caso contrário, desfaz a transação.
     *
     * @throws SQLException Se ocorrer um erro ao conectar ao banco de dados ou
     * ao executar a consulta.
     * @throws Exception Se ocorrer algum erro durante o processo de inserção.
     */
    public void inserir() throws Exception {
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
            System.out.println("Doacao inserida com sucesso!");

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

    /**
     * Lista todas as doações no banco de dados, incluindo as informações sobre
     * os doadores. A consulta realiza um JOIN entre as tabelas de doadores e
     * doações e exibe o tipo, quantidade, data da doação e o e-mail e ID do
     * doador.
     *
     * @throws Exception Se ocorrer um erro ao conectar ao banco de dados ou ao
     * executar a consulta.
     *
     */
    public void listar_doadocao() throws Exception {
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
        } finally {

            try {
                if (conexao != null && !conexao.isClosed()) {
                    conexao.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }

        }
    }

    /**
     * Método relatorio que faz a conexão com o banco de dados e efetua o
     * salvamento de todos os cadastros de doadores em um arquivo de texto.
     *
     * @throws SQLException Se ocorrer um erro ao consultar o banco de dados.
     * @throws IOException Se ocorrer um erro ao gravar o relatório no arquivo.
     */
    public void relatorio() throws SQLException, IOException {
        Connection conexao = new Conexao().getConexao();
        String sql_r = "select *from doadores inner join doacoes on fk_usuarios_doadores_id=fk_doadores_doadores_id";
        String arquivo = "relatorio.txt";
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo, true))) {
            try {
                PreparedStatement comando_rel = conexao.prepareStatement(sql_r);
                ResultSet rsr = comando_rel.executeQuery();
                escritor.write("Relatorio de Doaçoes");
                escritor.newLine();
                while (rsr.next()) {
                    int id = rsr.getInt("fk_doadores_doadores_id");
                    String email = rsr.getString("doadores_email");
                    String dd_tipo = rsr.getString("doacoes_tipo");
                    String dd_quant = rsr.getString("doacoes_quant");
                    String dd_date = rsr.getString("doacoes_date");

                    escritor.write("-------------------------------------------");
                    escritor.newLine();
                    escritor.write("email do doador:" + email);
                    escritor.newLine();
                    escritor.write("id do doador" + id);
                    escritor.newLine();
                    escritor.write("tipo da doaçao: " + dd_tipo);
                    escritor.newLine();
                    escritor.write("quantidade:" + dd_quant);
                    escritor.newLine();
                    escritor.write("data da doaçao: " + dd_date);
                    escritor.newLine();

                }
                escritor.write("----------------------------------------");
                escritor.newLine();

            } catch (SQLException e) {
                System.out.println(e);
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                // Fecha a conexão com o banco de dados
                if (conexao != null && !conexao.isClosed()) {
                    conexao.close();
                }
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }

    }
}
