package ong;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe para representar objetos do tipo Refugiados, contendo atributos e
 * métodos relacionados a essa entidade
 *
 * @author Janiele
 * @since 17/11/2024.
 */
public class Refugiados extends Usuarios {

    private String estado;
    private int refugiados_id;

    /**
     * Construtor Padrao da classe Refugiados.
     *
     * @param nome String - recebe o nome
     * @param data_nasc String - recebe a data de nascimento
     * @param nacionalidade String - recebe a nacionalidade
     * @param estado String - recebe o estado (legal ou ilegal)
     */
    public Refugiados(String nome, String data_nasc, String nacionalidade, String estado) {
        super(nome, data_nasc, nacionalidade);
        this.estado = estado;
    }

    public int getRefugiados_id() {
        return refugiados_id;
    }

    public void setRefugiados_id(int refugiados_id) {
        this.refugiados_id = refugiados_id;
    }

    /**
     * Método inserir que é a sobrescrita do método herdado da classe Usuarios,
     * nessa implementação, esse método faz a conexão com o banco de dados e
     * insere um novo cadastro de refugiados(inserindo dados na tabela usuarios
     * e refugiados.
     *
     * @throws Exception Se ocorrer um erro ao tentar estabelecer a conexão com
     * o banco de dados.
     *
     */
    @Override
    public void inserir() throws Exception {
        // conecta com o banco  
        Connection conexao = new Conexao().getConexao();
        // Inserir usuário na tabela 'usuarios' (tabela genérica para usuários)
        String sqlUsuario = "INSERT INTO usuarios(nome, nacionalidade, Data_nasc) VALUES(?, ?, ?)";
        //INSERT INTO `banco_refugiados`.`refugiados`(`refu_estado`, `fk_usuarios_refu_id`) VALUES ('ilegal', '7');
        //String sqlRefugiado = "INSERT INTO refugiados (refu_estado, fk_usuarios_refu_id) VALUES(?, ?)";
        String sqlRefugiado = "INSERT INTO refugiados ( refu_estado, fk_usuarios_refu_id) VALUES(?, ?)";
        try {
            // Começar uma transação para garantir consistência
            conexao.setAutoCommit(false);

            //inserindo dados na tabela usuários
            PreparedStatement comandoUsuario = conexao.prepareStatement(sqlUsuario, PreparedStatement.RETURN_GENERATED_KEYS);
            comandoUsuario.setString(1, this.nome);
            comandoUsuario.setString(2, this.nacionalidade);
            comandoUsuario.setString(3, this.data_nasc);
            comandoUsuario.executeUpdate();

            // Obter o id gerado automaticamente (pois o banco utiliza auto incremento)
            ResultSet rs = comandoUsuario.getGeneratedKeys();
            rs.next();
            int refugiados_id = rs.getInt(1);

            // Inserindo dados na tabela refugiados
            PreparedStatement comandoRefugiado = conexao.prepareStatement(sqlRefugiado);
            comandoRefugiado.setString(1, this.estado);
            comandoRefugiado.setInt(2, refugiados_id);
            comandoRefugiado.executeUpdate();

            // Confirmar a transação
            conexao.commit();
            System.out.println("Refugiado inserido com sucesso!");

            // Fechar as conexões
            comandoUsuario.close();
            comandoRefugiado.close();
            conexao.setAutoCommit(true);

        } catch (Exception e) {
            try {
                // Em caso de erro, desfaz a transação
                conexao.rollback();
            } catch (SQLException rollbackEx) {
                System.out.println("Erro ao reverter a transação: " + rollbackEx.getMessage());
            }
            System.out.println("Erro ao inserir refugiado: " + e.getMessage()); //retorno para o caso de dar erro no processo;
        }
    }

    /**
     * Método Listar,que é a sobrescrita do método herdado da classe Usuarios,
     * nessa implementação, esse método faz a conexão com o banco de dados e faz
     * a listagem de todos os cadastros de Refugiados.
     *
     * @throws SQLException Se ocorrer um erro ao tentar estabelecer a conexão
     * com o banco de dados.
     */
    @Override
    public void listar() throws SQLException {

        Connection conexao = new Conexao().getConexao();
        String sqlListar = "SELECT *FROM usuarios join  refugiados on usu_id = fk_usuarios_refu_id;";
        try {
            PreparedStatement comandoListar = conexao.prepareStatement(sqlListar);
            ResultSet rs2 = comandoListar.executeQuery();

            while (rs2.next()) {
                System.out.println("id: " + rs2.getInt("usu_id"));
                System.out.println("nome: " + rs2.getString("nome"));
                System.out.println("nacionalidade: " + rs2.getString("nacionalidade"));
                System.out.println("data de nascimento:" + rs2.getString("Data_nasc"));
                System.out.println("estado: " + rs2.getString("refu_estado"));
                System.out.println("-----------------------------------------------");
            }

            comandoListar.close();

        } catch (SQLException e) {
            System.out.println(e);
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
     * Método Listar,que é a sobrecarga do método listar(que faz a listagem de
     * todos os cadastros de refugiados) e que é void, nessa implementação, esse
     * método faz a conexão com o banco de dados e recebe como parametro o id
     * para fazer a consulta especifica de um cadastro de refugiados, e
     * linstando o mesmo.
     *
     * @throws SQLException Se ocorrer um erro ao tentar estabelecer a conexão
     * com o banco de dados.
     * @param id_consulta int - o id especifico do usuario
     */
    //SOBRECARGA
    public void listar(int id_consulta) throws SQLException {

        this.refugiados_id = id_consulta;
        Connection conexao = new Conexao().getConexao();
        //SELECT * FROM usuarios inner join refugiados on usu_id = fk_usuarios_refu_id where fk_usuarios_refu_id = 1 
        String sqlListart = "SELECT * FROM usuarios inner join refugiados on usu_id = fk_usuarios_refu_id where fk_usuarios_refu_id = ? ";
        try {

            PreparedStatement comandoListart = conexao.prepareStatement(sqlListart);
            comandoListart.setInt(1, this.refugiados_id);
            ResultSet rs2 = comandoListart.executeQuery();

            while (rs2.next()) {
                System.out.println("id: " + rs2.getInt("usu_id"));
                System.out.println("nome: " + rs2.getString("nome"));
                System.out.println("nacionalidade: " + rs2.getString("nacionalidade"));
                System.out.println("data de nascimento:" + rs2.getString("Data_nasc"));
                System.out.println("estado: " + rs2.getString("refu_estado"));
                System.out.println("--------------------------------------------");
            }
            comandoListart.close();

        } catch (SQLException e) {
            System.out.println(e);
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
     * Método verificar que faz a conexão com o banco de dados e recebe como
     * parametro o id, para verificar se ha algum cadastro refugiados com o id
     * inserido.
     *
     * @throws SQLException Se ocorrer um erro ao tentar estabelecer a conexão
     * com o banco de dados.
     * @param id_verificar int - o id para verificar se o cadastro existe
     * @return boolean - retorna verdadeiro se o cadastro existir ou falso caso
     * nao exista.
     */
    public boolean verificar(int id_verificar) throws SQLException {

        Connection conexao = new Conexao().getConexao();
        //SELECT fk_usuarios_refu_id FROM refugiados WHERE fk_usuarios_refu_id = 3;
        String sqlVerificar = "SELECT fk_usuarios_refu_id FROM refugiados WHERE fk_usuarios_refu_id = ? ";
        try {
            PreparedStatement comandoVerificar = conexao.prepareStatement(sqlVerificar);
            comandoVerificar.setInt(1, id_verificar);
            ResultSet rs = comandoVerificar.executeQuery();

            if (rs.next()) {
                int ver = rs.getInt("fk_usuarios_refu_id");
                if (ver == id_verificar) {
                    System.out.println("Id encontrado: ");
                    return true;

                } else {
                    System.out.println("opa id nao confere");
                    return false;
                }

            } else {
                System.out.println("opa id nao confere");
                return false;
            }

        } catch (SQLException e) {
            System.out.println(e);
            return false;

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
     * Método alterar que faz a conexão com o banco de dados e efetua a
     * alteração de alguns dos cadastros de refugiados.
     *
     * @throws SQLException Se ocorrer um erro ao tentar estabelecer a conexão
     * com o banco de dados.
     */
    public void alterar() throws SQLException {
        Connection conexao = new Conexao().getConexao();
        //UPDATE  usuarios  SET nome = "Jane Rodriguez ", Data_nasc= "23/12/1981", nacionalidade ="Coreana" WHERE  usu_id= 4;
        //UPDATE  refugiados SET refu_estado = "Legal" WHERE fk_usuarios_refu_id= 4;
        String sqlAlterar = "UPDATE  usuarios  SET nome =?, Data_nasc=?, nacionalidade=? WHERE  usu_id=?";
        String sqlAlterarRefu = "UPDATE  refugiados SET refu_estado = ? WHERE fk_usuarios_refu_id= ?";
        try {
            PreparedStatement comandoAlterar = conexao.prepareStatement(sqlAlterar);
            comandoAlterar.setString(1, this.nome);
            comandoAlterar.setString(2, this.data_nasc);
            comandoAlterar.setString(3, this.nacionalidade);
            comandoAlterar.setInt(4, this.refugiados_id);
            comandoAlterar.executeUpdate();

            PreparedStatement comandoAlterardd = conexao.prepareStatement(sqlAlterarRefu);
            comandoAlterardd.setString(1, this.estado);
            comandoAlterardd.setInt(2, this.refugiados_id);

            System.out.println("alterado com sucesso");
            comandoAlterar.close();
            comandoAlterardd.close();

        } catch (SQLException e) {
            System.out.println(e);

        }
    }

    /**
     * Método excluir que faz a conexão com o banco de dados e efetua a exclusão
     * de algum dos cadastros de refugiados.
     *
     * @throws SQLException Se ocorrer um erro ao tentar estabelecer a conexão
     * com o banco de dados.
     */
    public void excluir() throws SQLException {
        Connection conexao = new Conexao().getConexao();
        //DELETE FROM refugiados WHERE fk_usuarios_refu_id= 18
        //DELETE FROM usuarios WHERE usu_id = 18
        String sqlExcluir_refu = "DELETE FROM refugiados WHERE fk_usuarios_refu_id = ? ";
        String sqlExcluir_usu = "DELETE FROM usuarios WHERE usu_id = ?";
        try {
            PreparedStatement comandoExcluir = conexao.prepareStatement(sqlExcluir_refu);
            comandoExcluir.setInt(1, this.refugiados_id);
            comandoExcluir.executeUpdate();

            PreparedStatement comando_excluir_r = conexao.prepareStatement(sqlExcluir_usu);
            comando_excluir_r.setInt(1, this.refugiados_id);
            comando_excluir_r.executeUpdate();

            System.out.println("Refugiado apagado com sucesso");
            comandoExcluir.close();
            comando_excluir_r.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

    }

    /**
     * Método relatorio que faz a conexão com o banco de dados e efetua o
     * salvamento de todos os cadastros de refugiados em um arquivo de texto.
     *
     * @throws SQLException Se ocorrer um erro ao tentar estabelecer a conexão
     * com o banco de dados.
     * @throws IOException Se ocorrer um erro ao tentar criar o arquivo de
     * texto.
     */

    public void relatorio() throws SQLException, IOException {
        Connection conexao = new Conexao().getConexao();
        String sql_relatorio = "select *from usuarios join  refugiados on usu_id=fk_usuarios_refu_id";
        // Caminho do arquivo onde o relatório será salvo
        String arquivo = "relatorio.txt";
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo, true))) {
            try {
                PreparedStatement comandoRelatorio = conexao.prepareStatement(sql_relatorio);
                ResultSet rsd = comandoRelatorio.executeQuery();
                escritor.write("Relatorio Refugiados");
                escritor.newLine();
                while (rsd.next()) {
                    int id_txt = rsd.getInt("usu_id");
                    String nome = rsd.getString("nome");
                    String nacionalidade1 = rsd.getString("nacionalidade");
                    String data = rsd.getString("Data_nasc");
                    String estado = rsd.getString("refu_estado"); // com a bola
                    // Escreve no arquivo
                    escritor.write("----------------------------------------");
                    escritor.newLine();
                    escritor.write("nome: " + nome);
                    escritor.newLine();
                    escritor.write("data de nascimento: " + data);
                    escritor.newLine();
                    escritor.write("nacionalidade: " + nacionalidade1);
                    escritor.newLine();
                    escritor.write("estado: " + estado);
                    escritor.newLine();
                    escritor.write("id: " + id_txt);
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
