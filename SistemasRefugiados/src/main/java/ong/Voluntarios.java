package ong;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A Classe "Voluntarios" é uma subclasse que representa os voluntários da ONG.
 * Representa quem vai prestar serviços na ong, ela é uma classe dependente
 * da classe "Usuarios" que é a classe mãe.
 * 
 * Ela herda varios atributos de Usuarios, como nome, data de nascimento e
 * nacionalidade.
 * Todos os outros métodos estão comentados e explicados no decorrer da classe.
 * Estende a classe {@link Usuarios}.
 * 
 * @author Arthur
 * @since 16/11/2024
 */
public class Voluntarios extends Usuarios {

    private String email;
    private String habilidades;
    private int volu_id;

    /**
     * Construtor da classe Voluntarios.
     *
     * @param nome          Nome do voluntário.
     * @param data_nasc     Data de nascimento do voluntário.
     * @param nacionalidade Nacionalidade do voluntário.
     * @param email         Email do voluntário.
     * @param habilidades   Habilidades do voluntário.
     */
    public Voluntarios(String nome, String data_nasc, String nacionalidade, String email, String habilidades) {
        super(nome, data_nasc, nacionalidade);
        this.email = email;
        this.habilidades = habilidades;
    }

    /**
     * Obtém o ID do voluntário.
     *
     * @return ID do voluntário.
     */
    public int getVolu_id() {
        return volu_id;
    }

    /**
     * Define o ID do voluntário.
     *
     * @param volu_id ID do voluntário.
     */
    public void setVolu_id(int volu_id) {
        this.volu_id = volu_id;
    }

    /**
     * Insere um novo voluntário no banco de dados.
     * Realiza inserções nas tabelas `usuarios` e `voluntarios`.
     *
     * @throws SQLException Em caso de erro na execução da transação.
     */
    @Override
    public void inserir() throws SQLException {
        Connection conexao = new Conexao().getConexao();
        String sqlUsuario = "INSERT INTO usuarios(nome, nacionalidade, Data_nasc) VALUES(?, ?, ?)";
        String sqlVoluntario = "INSERT INTO voluntarios (fk_usuarios_volu_id, volu_email, volu_habilidades) VALUES(?, ?, ?)";

        try {
            conexao.setAutoCommit(false);

            PreparedStatement comandoUsuario = conexao.prepareStatement(sqlUsuario, PreparedStatement.RETURN_GENERATED_KEYS);
            comandoUsuario.setString(1, this.nome);
            comandoUsuario.setString(2, this.nacionalidade);
            comandoUsuario.setString(3, this.data_nasc);
            comandoUsuario.executeUpdate();

            ResultSet rs = comandoUsuario.getGeneratedKeys();
            rs.next();
            int voluntarios_id = rs.getInt(1);

            PreparedStatement comandoVoluntario = conexao.prepareStatement(sqlVoluntario);
            comandoVoluntario.setInt(1, voluntarios_id);
            comandoVoluntario.setString(2, this.email);
            comandoVoluntario.setString(3, this.habilidades);
            comandoVoluntario.executeUpdate();

            conexao.commit();
            System.out.println("Voluntario inserido com sucesso!");

            comandoUsuario.close();
            comandoVoluntario.close();
            conexao.setAutoCommit(true);

        } catch (Exception e) {
            try {
                conexao.rollback();
            } catch (SQLException rollbackEx) {
                System.out.println("Erro ao reverter a transação: " + rollbackEx.getMessage());
            }
            System.out.println("Erro ao inserir Voluntario: " + e.getMessage());
        }
    }

    /**
     * Lista todos os voluntários cadastrados no banco de dados.
     *
     * @throws SQLException Em caso de erro ao acessar o banco de dados.
     */
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

    /**
     * Lista os dados de um voluntário com base no email informado.
     *
     * @param l_email Email do voluntário a ser listado.
     * @throws SQLException Em caso de erro ao acessar o banco de dados.
     */
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

    /**
     * Verifica se um voluntário existe no banco de dados com o email e ID fornecidos.
     *
     * @param email_verificar Email a ser verificado.
     * @param id_verificar    ID a ser verificado.
     * @return {@code true} se o email e ID corresponderem a um voluntário existente,
     *         {@code false} caso contrário.
     * @throws SQLException Em caso de erro ao acessar o banco de dados.
     */
    public boolean verificar(String email_verificar, int id_verificar) throws SQLException {
        Connection conexao = new Conexao().getConexao();
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

    /**
     * Atualiza os dados de um voluntário existente no banco de dados.
     *
     * @throws SQLException Em caso de erro ao executar a atualização.
     */
    public void alterar() throws SQLException {
        Connection conexao = new Conexao().getConexao();
        String sqlAlterar = "UPDATE  usuarios  SET nome =?, Data_nasc=?, nacionalidade=? WHERE  usu_id=?";
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
            comandoAlterardd.executeUpdate();

            System.out.println("alterado com sucesso");
            comandoAlterar.close();
            comandoAlterardd.close();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Exclui um voluntário do banco de dados.
     * Remove o registro do voluntário da tabela `voluntarios` com base no email e
     * também remove o registro correspondente na tabela `usuarios` com base no nome.
     *
     * @throws SQLException Em caso de erro ao acessar ou modificar o banco de dados.
     */
    public void excluir() throws SQLException {
        Connection conexao = new Conexao().getConexao();
        String sqlExcluir_volu = "DELETE FROM voluntarios WHERE volu_email= ?";
        String sqlExcluir_usu = "DELETE FROM usuarios WHERE nome= ?";
        try {
            PreparedStatement comandoExcluir = conexao.prepareStatement(sqlExcluir_volu);
            comandoExcluir.setString(1, this.email);
            comandoExcluir.executeUpdate();

            PreparedStatement comando_excluir_d = conexao.prepareStatement(sqlExcluir_usu);
            comando_excluir_d.setString(1, this.nome);
            comando_excluir_d.executeUpdate();

            System.out.println("Voluntario apagado com sucesso");
            comandoExcluir.close();
            comando_excluir_d.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Gera um relatório de todos os voluntários e salva em um arquivo de texto.
     *
     * @throws SQLException Em caso de erro ao acessar o banco de dados.
     */
    public void relatorio() throws SQLException {
        Connection conexao = new Conexao().getConexao();
        String sql_relatorio = "SELECT * FROM usuarios inner join voluntarios on usu_id = fk_usuarios_volu_id;";
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
                    String habilidade = rsd.getString("volu_habilidades");
                    escritor.write("----------------------------------------");
                    escritor.newLine();
                    escritor.write("nome: " + nome);
                    escritor.newLine();
                    escritor.write("email: " + emaild);
                    escritor.newLine();
                    escritor.write("data de nascimento: " + data);
                    escritor.newLine();
                    escritor.write("nacionalidade: " + nacionalidade1);
                    escritor.newLine();
                    escritor.write("habilidade: " + habilidade);
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
                if (conexao != null && !conexao.isClosed()) {
                    conexao.close();
                }
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}
