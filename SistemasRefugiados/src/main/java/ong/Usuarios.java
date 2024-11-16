
package ong;
import java.sql.Connection;
import java.sql.PreparedStatement;


public class Usuarios {
      
    protected String nome;
    protected String data_nasc;
    protected String nacionalidade;

    public Usuarios(String nome, String data_nasc, String nacionalidade){
        this.nome = nome;
        this.data_nasc = data_nasc;
        this.nacionalidade = nacionalidade;
    }
    public void inserir() {
//        grava um aluno no banco
//        Conexao c = new Conexao();
//        Connection conexao = c.getConexao();
        Connection conexao = new Conexao().getConexao();
        String sql = "INSERT INTO usuarios(nome, nacionalidade, Data_nasc) VALUES(?, ?, ?)";
        try{
             PreparedStatement comando = conexao.prepareStatement(sql);
             comando.setString(1, this.nome);
             comando.setString(2, this.nacionalidade);
             comando.setString(3, this.data_nasc);
             
             comando.execute();
             comando.close();
            
        }catch(Exception e){
            System.out.println("e");
        }
    }
} 

