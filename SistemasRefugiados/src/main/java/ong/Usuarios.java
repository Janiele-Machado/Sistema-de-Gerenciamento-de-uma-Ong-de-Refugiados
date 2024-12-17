package ong;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class Usuarios {

    protected String nome;
    protected String data_nasc;
    protected String nacionalidade;

    public Usuarios(String nome, String data_nasc, String nacionalidade) {
        this.nome = nome;
        this.data_nasc = data_nasc;
        this.nacionalidade = nacionalidade;
    }

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
