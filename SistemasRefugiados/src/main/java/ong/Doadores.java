package ong;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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

    public void setDoadores_id(int dd) {
        this.doadores_id = dd;

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

    public boolean verificar(String email_verificar, int id_verificar) {

        Connection conexao = new Conexao().getConexao();
        String sqlVerificar = "SELECT fk_usuarios_doadores_id  FROM doadores WHERE doadores_email= ?";
        try {
            PreparedStatement comandoVerificar = conexao.prepareStatement(sqlVerificar);
            comandoVerificar.setString(1, email_verificar);
            ResultSet rs = comandoVerificar.executeQuery();

            if (rs.next()) {
                int ver = rs.getInt("fk_usuarios_doadores_id");
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

    @Override
    public void listar() {
        Connection conexao = new Conexao().getConexao();
        String sqlListar = "select *from usuarios join  doadores on usu_id= fk_usuarios_doadores_id;";
        try {
            PreparedStatement comandoListar = conexao.prepareStatement(sqlListar);
            ResultSet rs2 = comandoListar.executeQuery();

            while (rs2.next()) {
                System.out.println("id: " + rs2.getInt("usu_id"));
                System.out.println("nome: " + rs2.getString("nome"));
                System.out.println("nacionalidade: " + rs2.getString("nacionalidade"));
                System.out.println("data de nascimento:" + rs2.getString("Data_nasc"));
                System.out.println("email: " + rs2.getString("doadores_email"));
                System.out.println("--------------------------------------------");
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
    public void listar(String l_email) {
        this.email = l_email;
        Connection conexao = new Conexao().getConexao();
        String sqlListart = "select *from usuarios join  doadores on usu_id= fk_usuarios_doadores_id where doadores_email=?";
        try {
            PreparedStatement comandoListart = conexao.prepareStatement(sqlListart);
            comandoListart.setString(1, this.email);
            ResultSet rs2 = comandoListart.executeQuery();

            while (rs2.next()) {
                System.out.println("id: " + rs2.getInt("usu_id"));
                System.out.println("nome: " + rs2.getString("nome"));
                System.out.println("nacionalidade: " + rs2.getString("nacionalidade"));
                System.out.println("data de nascimento:" + rs2.getString("Data_nasc"));
                System.out.println("email: " + rs2.getString("doadores_email"));
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

    public void excluir() {
        Connection conexao = new Conexao().getConexao();
        String sqlExcluir_doador = "DELETE FROM  doadores WHERE doadores_email= ?";
        String sqlExcluir_usu = "DELETE FROM usuarios WHERE nome= ?";
        try {
            PreparedStatement comandoExcluir = conexao.prepareStatement(sqlExcluir_doador);
            comandoExcluir.setString(1, this.email);
            comandoExcluir.executeUpdate();

            PreparedStatement comando_excluir_d = conexao.prepareStatement(sqlExcluir_usu);
            comando_excluir_d.setString(1, this.nome);
            comando_excluir_d.executeUpdate();

            System.out.println("Doador apagado com sucesso");
            comandoExcluir.close();
            comando_excluir_d.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

    }

    public void alterar() {
        Connection conexao = new Conexao().getConexao();
        String sqlAlterar = "UPDATE  usuarios  SET nome =?, Data_nasc=?, nacionalidade=? WHERE  usu_id=?";
        String sqlAlterardoadores = "UPDATE  doadores SET doadores_email=? WHERE fk_usuarios_doadores_id=?";
        try {
            PreparedStatement comandoAlterar = conexao.prepareStatement(sqlAlterar);
            comandoAlterar.setString(1, this.nome);
            comandoAlterar.setString(2, this.data_nasc);
            comandoAlterar.setString(3, this.nacionalidade);
            comandoAlterar.setInt(4, this.doadores_id);
            comandoAlterar.executeUpdate();

            PreparedStatement comandoAlterardd = conexao.prepareStatement(sqlAlterardoadores);
            comandoAlterardd.setString(1, this.email);
            comandoAlterardd.setInt(2, this.doadores_id);

            System.out.println("alterado com sucesso");
            comandoAlterar.close();
            comandoAlterardd.close();

        } catch (SQLException e) {
            System.out.println(e);

        }
    }

    public void relatorio() {
        Connection conexao = new Conexao().getConexao();
        String sql_relatorio = "SELECT * FROM usuarios inner join doadores on usu_id = fk_usuarios_doadores_id;";
        // Caminho do arquivo onde o relatório será salvo
        String arquivo = "relatorio.txt";
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo, true))) {
            try {
                PreparedStatement comandoRelatorio = conexao.prepareStatement(sql_relatorio);
                ResultSet rsd = comandoRelatorio.executeQuery();
                escritor.write("Relatorio Doadores");
                escritor.newLine();
                
                while (rsd.next()) {
                    int id_txt = rsd.getInt("usu_id");
                    String nome = rsd.getString("nome");
                    String nacionalidade1 = rsd.getString("nacionalidade");
                    String data = rsd.getString("Data_nasc");
                    String emaild = rsd.getString("doadores_email");  
                    // Escreve no arquivo
                    escritor.write("----------------------------------------");
                    escritor.newLine();
                    escritor.write("nome: " + nome);
                    escritor.newLine();
                    escritor.write("email: " + emaild );
                    escritor.newLine();
                    escritor.write("data de nascimento: " + data);
                    escritor.newLine();
                    escritor.write("nacionalidade: " + nacionalidade1);
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
