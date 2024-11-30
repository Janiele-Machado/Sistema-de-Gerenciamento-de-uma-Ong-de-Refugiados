package ong;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

public class Doadores extends Usuarios {

    private String email;
    private int doadores_id;

    public Doadores(String nome, String data_nasc, String nacionalidade, String email) {
        super(nome, data_nasc, nacionalidade);
        this.email = email;

    }

    public int getDoadores_id() {
        return doadores_id;
    }

    @Override
    public void inserir() {
        // conecta com o banco  
        Connection conexao = new Conexao().getConexao();
        // Inserir usuário na tabela 'usuarios' (tabela genérica para usuários)
        String sqlUsuario = "INSERT INTO usuarios(nome, nacionalidade, Data_nasc) VALUES(?, ?, ?)";
        String sqlDoador = "INSERT INTO doadores(fk_usuarios_doadores_id,  doadores_email ) VALUES(?, ?)";
        try {
            // Começar uma transação para garantir consistência
            conexao.setAutoCommit(false);

            PreparedStatement comandoUsuario = conexao.prepareStatement(sqlUsuario, PreparedStatement.RETURN_GENERATED_KEYS);
            comandoUsuario.setString(1, this.nome);
            comandoUsuario.setString(2, this.nacionalidade);
            comandoUsuario.setString(3, this.data_nasc);
            comandoUsuario.executeUpdate();

            // Obter o id gerado automaticamente (caso o banco utilize auto incremento)
            ResultSet rs = comandoUsuario.getGeneratedKeys();
            rs.next();
            doadores_id = rs.getInt(1);

            // Inserir dados na tabela 'doadores'
            PreparedStatement comandoDoador = conexao.prepareStatement(sqlDoador);
            comandoDoador.setInt(1, this.doadores_id);
            comandoDoador.setString(2, this.email);
            comandoDoador.executeUpdate();

            // Confirmar a transação
            conexao.commit();
            System.out.println("Doador inserido com sucesso!");

            // Fechar as conexões
            comandoUsuario.close();
            comandoDoador.close();
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

    public boolean verifivar(String email_verificar, int id_verificar) {

        Connection conexao = new Conexao().getConexao();
        String sqlVerificar = "SELECT doadores_id  FROM doadores WHERE doadores_email= ?";
        try {
            PreparedStatement comandoVerificar = conexao.prepareStatement(sqlVerificar);
            comandoVerificar.setString(1, email_verificar);
            ResultSet rs = comandoVerificar.executeQuery();

            if (rs.next()) {
                int ver = rs.getInt("doadores_id");
                if (ver == id_verificar) {
                    System.out.println("ola ");
                    return true;

                } else {
                    System.out.println("ops email ou id nao conferem");
                    return false;
                }

            } else {
                System.out.println("email nao encontrado");
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

    public void listar() {
        Connection conexao = new Conexao().getConexao();
        String sqlListar = "select *from usuarios join  doadores on usu_id=doadores_id;";
        try {
            PreparedStatement comandoListar = conexao.prepareStatement(sqlListar);
            ResultSet rs2 = comandoListar.executeQuery();

            while (rs2.next()) {
                System.out.println("id: " + rs2.getInt("usu_id"));
                System.out.println("nome: " + rs2.getString("nome"));
                System.out.println("nacionalidade: " + rs2.getString("nacionalidade"));
                System.out.println("data de nascimento:" + rs2.getString("Data_nasc"));
                System.out.println("email: " + rs2.getString("doadores_email"));
            }

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
