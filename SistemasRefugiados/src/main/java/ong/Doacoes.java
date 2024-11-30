
package ong;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class Doacoes {
   private String doacoes_tipo;
   private String doacoes_quant;
   private String  doacoes_date;
   private int doadorid;
    
   public  Doacoes(String doacoes_tipo, String doacoes_quant, String  doacoes_date,int doadorid){
       this.doacoes_tipo= doacoes_tipo;
       this.doacoes_quant= doacoes_quant;
       this.doacoes_date = doacoes_date;
       this.doadorid= doadorid;
       
   }
  public void setDoadorid(int dd_id){
      doadorid= dd_id;
      
      
      
  }
   
    public void inserir(){
       Connection conexao = new Conexao().getConexao();
         String sqldoacao =  "INSERT INTO doacoes (doacoes_tipo, doacoes_quant ,doacoes_date, fk_doadores_doadores_id) VALUES(?,?,?,?)";
         
            try{
                // Começar uma transação para garantir consistência
                        conexao.setAutoCommit(false);
                        PreparedStatement comandoDoacoes = conexao.prepareStatement (sqldoacao, PreparedStatement.RETURN_GENERATED_KEYS);
                        comandoDoacoes.setString(1, this.doacoes_tipo);
                        comandoDoacoes.setString(2, this.doacoes_quant);
                        comandoDoacoes.setString(3, this.doacoes_date);
                        comandoDoacoes.setInt(4,  this.doadorid);
                        comandoDoacoes.executeUpdate();
                        
                        // Confirmar a transação
                        conexao.commit();
                        System.out.println("Doador inserido com sucesso!");
                        
                        // Fechar as conexões
                    
                    comandoDoacoes.close();
                    conexao.setAutoCommit(true);
                }catch(Exception e){
                    try {
                    // Em caso de erro, desfaz a transação
                    conexao.rollback();
                    } catch (SQLException rollbackEx) {
                    System.out.println("Erro ao reverter a transação: " + rollbackEx.getMessage());
                     }
                    System.out.println("Erro ao inserir doador: " + e.getMessage());
                    
                    }
           }

                        
                        
                        
            
         
         
   }
       
   

    
   
   
    
    
    

