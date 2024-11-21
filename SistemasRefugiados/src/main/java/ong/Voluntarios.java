package ong;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Voluntarios extends Usuarios {

    private String email;
    private String habilidades;

    public Voluntarios(String nome, String data_nasc, String nacionalidade, String email, String habilidades) {
        super(nome, data_nasc, nacionalidade);
        this.email = email;
        this.habilidades = habilidades;

    }

    @Override
    public void inserir() {
        // conecta com o banco  
        Connection conexao = new Conexao().getConexao();
        // Inserir usuário na tabela 'usuarios' (tabela genérica para usuários)
        String sqlUsuario = "INSERT INTO usuarios(nome, nacionalidade, Data_nasc) VALUES(?, ?, ?)";
        //INSERT INTO `banco_refugiados`.`voluntarios` (`volu_habilidades`,`volu_email`, `fk_usuarios_volu_id`)VALUES ('conhecimento de informática', 'dol@gmail.com', '14');
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
            comandoVoluntario.setInt(1,voluntarios_id);
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

}
