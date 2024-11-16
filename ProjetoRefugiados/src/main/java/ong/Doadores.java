package ong;
import java.time.LocalDate;
public class Doadores extends Usuario {
   private LocalDate doacoes_date;
   
   private  int id;
   private  String email;
    private String doacoes_tipo;
    private float doacoes_quant;
    
            public Doadores(String nome, String data_nasc, String nacionalidade,  String email, int id){
                super(nome,data_nasc,nacionalidade);
                this.email= email;
                this.id=id;
                this.doacoes_quant = 0; // Inicializa a quantidade de doações com 0
                this.doacoes_tipo = "Dinheiro"; // Valor padrão para tipo de doação
                this.doacoes_date = LocalDate.now(); // Armazena a data atual como data da primeira doação
            }
            
            public void doar_dinheiro(float quant_d){
                if(quant_d>0){
                    this.doacoes_quant+= quant_d;
                    this.doacoes_tipo= "Dinheiro";
                    this.doacoes_date=LocalDate.now();
                    System.out.println("A doacao de "+quant_d+" R$ foi concluida muito obrigado pela doacao ");                                             
                    
                }else{
                    System.out.println("desculpa quantidade devera ser maior que 0");
                }
                
           
            } public void doar_alimento(float alimento_kilo){
                    if(alimento_kilo>0){
                        this.doacoes_quant+= alimento_kilo;
                        this.doacoes_tipo="Alimento nao perecivel";
                        this.doacoes_date=LocalDate.now();
                             
                }else{
                    System.out.println("desculpa quantidade devera ser maior que 0");
                }
                    
                }
            
            public int getid(){
                return id;
            }
            public String getEmail(){
                return email;
            }
            public float getDoacoes(){
                return doacoes_quant;
            }
            
            
            
            
    
    
}
