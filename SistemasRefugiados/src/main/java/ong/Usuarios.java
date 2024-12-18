package ong;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

/**
 * A classe Usuarios é a classe base (ou mãe) que representa um usuário em um
 * sistema que interage com um banco de dados. Ela fornece métodos para inserir
 * e listar usuários, que podem ser estendidos por outras classes como Doadores,
 * Refugiados e Voluntarios.
 *
 * As subclasses podem herdar atributos como nome, data de nascimento e
 * nacionalidade, além dos métodos de inserção e listagem de usuários.
 *
 * @author Matheus
 * @since 16/11/2024
 */
public class Usuarios {

    protected String nome;
    protected String data_nasc;
    protected String nacionalidade;

    /**
     * Construtor da classe Usuarios, utilizado para inicializar um novo objeto
     * de usuário com nome, data de nascimento e nacionalidade.
     *
     * @param nome O nome do usuário.
     * @param data_nasc A data de nascimento do usuário.
     * @param nacionalidade A nacionalidade do usuário.
     *
     */
    public Usuarios(String nome, String data_nasc, String nacionalidade) {
        this.nome = nome;
        this.data_nasc = data_nasc;
        this.nacionalidade = nacionalidade;
    }

    /**
     * Insere um novo usuário no banco de dados. Este método executa uma
     * inserção no banco de dados utilizando as informações fornecidas na
     * criação do objeto. Não retorna nenhum valor.
     *
     * @throws Exception Se ocorrer algum erro durante a execução da operação de
     * inserção no banco de dados.
     *
     */
    public void inserir() throws Exception {
//        grava um usuario no banco
//        Conexao c = new Conexao();
//        Connection conexao = c.getConexao();
        Connection conexao = new Conexao().getConexao();
        String sql = "INSERT INTO usuarios(nome, nacionalidade, Data_nasc) VALUES(?, ?, ?)";
        try {
            PreparedStatement comando = conexao.prepareStatement(sql);
            comando.setString(1, this.nome);
            comando.setString(2, this.nacionalidade);
            comando.setString(3, this.data_nasc);

            comando.execute();
            comando.close();

        } catch (Exception e) {
            System.out.println("e");
        }
    }

    /**
     * Lista todos os usuários presentes no banco de dados. Este método executa
     * uma consulta no banco de dados e imprime as informações de todos os
     * usuários cadastrados. Não retorna nenhum valor.
     *
     * @throws SQLException Se ocorrer algum erro durante a execução da consulta
     * no banco de dados.
     */
    public void listar() throws SQLException {
        Connection conexao = new Conexao().getConexao();
        String sql_listar_uso = "SELECT * FROM  banco_refugiados.usuarios;";
        try {
            PreparedStatement comando_listar_usu = conexao.prepareStatement(sql_listar_uso);
            ResultSet rs_usu = comando_listar_usu.executeQuery();
            while (rs_usu.next()) {
                System.out.println("nome: " + rs_usu.getString("nome"));
                System.out.println("nacionalidade: " + rs_usu.getString("nacionalidade"));
                System.out.println("data de nascimento: " + rs_usu.getString("Data_nasc"));
                System.out.println("id: " + rs_usu.getInt("usu_id"));
                System.out.println("--------------------------------------------------------------");

            }
            comando_listar_usu.close();

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
}
