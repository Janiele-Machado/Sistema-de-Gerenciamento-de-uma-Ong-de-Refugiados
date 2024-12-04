package ong;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Refugiados extends Usuarios {

    private String estado;
    private int refugiados_id;

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

    @Override
    public void inserir() {
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

    public void listar() {

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

    //SOBRECARGA
    public void listar(int id_consulta) {

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

    public boolean verificar(int id_verificar) {

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

    public void alterar() {
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

    public void excluir() {
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

}
