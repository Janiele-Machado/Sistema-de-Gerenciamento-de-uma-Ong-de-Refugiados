package ong;
import java.time.LocalDate;
public class Doadores extends Usuario {
   private LocalDate data_doacoes;
   private float valor;
   private  int id;
    
            public Doadores(String nome, String data_nasc, String nacionalidade, LocalDate data_doacoes, float valor, int id){
                super(nome,data_nasc,nacionalidade);
                this.valor=valor;
                this.data_doacoes= data_doacoes;
                this.id=id;
            }
            
    
    
}
