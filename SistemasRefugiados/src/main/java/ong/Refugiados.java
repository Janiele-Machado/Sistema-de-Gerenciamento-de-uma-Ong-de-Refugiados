package ong;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Refugiados extends Usuarios {

    private String estado;

    public Refugiados(String nome, String data_nasc, String nacionalidade, String estado) {
        super(nome, data_nasc, nacionalidade);
        this.estado = estado;
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
}
