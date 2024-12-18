package ong;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Voluntarios extends Usuarios {

    private String email;
    private String habilidades;
    private int volu_id;

    public Voluntarios(String nome, String data_nasc, String nacionalidade, String email, String habilidades) {
        super(nome, data_nasc, nacionalidade);
        this.email = email;
        this.habilidades = habilidades;

    }

    public int getVolu_id() {
        return volu_id;
    }

    public void setVolu_id(int volu_id) {
        this.volu_id = volu_id;
    }

    @Override
    public void inserir() throws SQLException {
        // conecta com o banco  
        Connection conexao = new Conexao().getConexao();
        // Inserir usuário na tabela 'usuarios' (tabela genérica para usuários)
        String sqlUsuario = "INSERT INTO usuarios(nome, nacionalidade, Data_nasc) VALUES(?, ?, ?)";
        //INSERT INTO banco_refugiados.voluntarios (volu_habilidades,volu_email, fk_usuarios_volu_id)VALUES ('conhecimento de informática', 'dol@gmail.com', '14');
        String sqlVoluntario = "INSERT INTO voluntarios (fk_usuarios_volu_id, volu_email, volu_habilidades) VALUES(?, ?, ?)";

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
            int voluntarios_id = rs.getInt(1);

            // Inserir dados na tabela 'voluntarios'
            PreparedStatement comandoVoluntario = conexao.prepareStatement(sqlVoluntario);
            comandoVoluntario.setInt(1, voluntarios_id);
            comandoVoluntario.setString(2, this.email);
            comandoVoluntario.setString(3, this.habilidades);
            comandoVoluntario.executeUpdate();

            // Confirmar a transação
            conexao.commit();
            System.out.println("Voluntario inserido com sucesso!");

            // Fechar as conexões
            comandoUsuario.close();
            comandoVoluntario.close();
            conexao.setAutoCommit(true);

        } catch (Exception e) {
            try {
                // Em caso de erro, desfaz a transação
                conexao.rollback();
            } catch (SQLException rollbackEx) {
                System.out.println("Erro ao reverter a transação: " + rollbackEx.getMessage());
            }
            System.out.println("Erro ao inserir Voluntario: " + e.getMessage());

        }
    }

    public void listar() throws SQLException {

        Connection conexao = new Conexao().getConexao();
        String sqlListar = "SELECT *FROM usuarios join  voluntarios on usu_id = fk_usuarios_volu_id;";
        try {
            PreparedStatement comandoListar = conexao.prepareStatement(sqlListar);
            ResultSet rs2 = comandoListar.executeQuery();

            while (rs2.next()) {
                System.out.println("id: " + rs2.getInt("usu_id"));
                System.out.println("nome: " + rs2.getString("nome"));
                System.out.println("nacionalidade: " + rs2.getString("nacionalidade"));
                System.out.println("data de nascimento:" + rs2.getString("Data_nasc"));
                System.out.println("email: " + rs2.getString("volu_email"));
                System.out.println("Habilidade:" + rs2.getString("volu_habilidades"));
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
    public void listar(String l_email) throws SQLException {
        this.email = l_email;
        Connection conexao = new Conexao().getConexao();
        String sqlListart = "select *from usuarios join voluntarios on usu_id= fk_usuarios_volu_id where volu_email= ?";
        try {
            PreparedStatement comandoListart = conexao.prepareStatement(sqlListart);
            comandoListart.setString(1, this.email);
            ResultSet rs2 = comandoListart.executeQuery();

            while (rs2.next()) {
                System.out.println("id: " + rs2.getInt("usu_id"));
                System.out.println("nome: " + rs2.getString("nome"));
                System.out.println("nacionalidade: " + rs2.getString("nacionalidade"));
                System.out.println("data de nascimento:" + rs2.getString("Data_nasc"));
                System.out.println("email: " + rs2.getString("volu_email"));
                System.out.println("Habilidade:" + rs2.getString("volu_habilidades"));
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

    public boolean verificar(String email_verificar, int id_verificar) throws SQLException {

        Connection conexao = new Conexao().getConexao();
        //SELECT fk_usuarios_volu_id  FROM voluntarios WHERE volu_email= "dol@gmail.com"
        String sqlVerificar = "SELECT fk_usuarios_volu_id  FROM voluntarios WHERE volu_email=? ";
        try {
            PreparedStatement comandoVerificar = conexao.prepareStatement(sqlVerificar);
            comandoVerificar.setString(1, email_verificar);
            ResultSet rs = comandoVerificar.executeQuery();

            if (rs.next()) {
                int ver = rs.getInt("fk_usuarios_volu_id");
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

    public void alterar() throws SQLException {
        Connection conexao = new Conexao().getConexao();
        String sqlAlterar = "UPDATE  usuarios  SET nome =?, Data_nasc=?, nacionalidade=? WHERE  usu_id=?";
        //UPDATE  voluntarios SET volu_email=?,volu_habilidades =? WHERE fk_usuarios_volu_id=?
        String sqlAlterarvolu = "UPDATE voluntarios SET volu_email=?,volu_habilidades =? WHERE fk_usuarios_volu_id=?";
        try {

            PreparedStatement comandoAlterar = conexao.prepareStatement(sqlAlterar);
            comandoAlterar.setString(1, this.nome);
            comandoAlterar.setString(2, this.data_nasc);
            comandoAlterar.setString(3, this.nacionalidade);
            comandoAlterar.setInt(4, this.volu_id);
            comandoAlterar.executeUpdate();

            PreparedStatement comandoAlterardd = conexao.prepareStatement(sqlAlterarvolu);
            comandoAlterardd.setString(1, this.email);
            comandoAlterardd.setString(2, this.habilidades);
            comandoAlterardd.setInt(3, this.volu_id);

            System.out.println("alterado com sucesso");
            comandoAlterar.close();
            comandoAlterardd.close();

        } catch (SQLException e) {
            System.out.println(e);

        }
    }

    public void relatorio() throws SQLException {
        Connection conexao = new Conexao().getConexao();
        String sql_relatorio = "SELECT * FROM usuarios inner join voluntarios on usu_id = fk_usuarios_volu_id;";
        // Caminho do arquivo onde o relatório será salvo
        String arquivo = "relatorio.txt";
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo, true))) {
            try {
                PreparedStatement comandoRelatorio = conexao.prepareStatement(sql_relatorio);
                ResultSet rsd = comandoRelatorio.executeQuery();
                escritor.write("Relatorio Voluntarios");
                escritor.newLine();
                while (rsd.next()) {
                    int id_txt = rsd.getInt("usu_id");
                    String nome = rsd.getString("nome");
                    String nacionalidade1 = rsd.getString("nacionalidade");
                    String data = rsd.getString("Data_nasc");
                    String emaild = rsd.getString("volu_email");
                    String habilidade = rsd.getString("volu_habilidades"); // com a bola
                    // Escreve no arquivo
                    escritor.write("----------------------------------------");
                    escritor.newLine();
                    escritor.write("nome: " + nome );
                    escritor.newLine();
                    escritor.write("email: " + emaild );
                    escritor.newLine();
                    escritor.write("data de nascimento: " + data);
                    escritor.newLine();
                    escritor.write("nacionalidade: " + nacionalidade1);
                    escritor.newLine();
                    escritor.write("habilidade: " + habilidade );
                    escritor.newLine();
                    escritor.write("id: " + id_txt );
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
