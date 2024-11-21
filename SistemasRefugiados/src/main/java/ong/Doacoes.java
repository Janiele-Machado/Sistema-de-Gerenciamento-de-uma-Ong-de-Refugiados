
package ong;

import java.time.LocalDate;


public class Doacoes {
   private String doacoes_tipo;
   private String doacoes_quant;
   private LocalDate doacoes_date;
    
   public  Doacoes(String doacoes_tipo, String doacoes_quant, LocalDate doacoes_date){
       this.doacoes_tipo= doacoes_tipo;
       this.doacoes_quant= doacoes_quant;
       this.doacoes_date = doacoes_date;
       
   }
    
    
    
}
