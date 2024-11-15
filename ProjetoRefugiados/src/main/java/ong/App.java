package ong;

import java.util.InputMismatchException;
import java.util.Scanner;
import static javax.management.Query.or;

public class App {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int opc_principal;
        int opc1=0;
        
        

        opc_principal = 0;

        while (opc_principal != 6) {
            try {

                System.out.println("-----Menu de Opcoes---------");
                System.out.println("1-Cadastro de usuarios");
                System.out.println("2-Registrar doacoes");
                System.out.println("3-Listagem de usuarios ");
                System.out.println("4-Editar Cadastros");
                System.out.println("5-Excluir Cadastros");
                System.out.println("6-Sair");
                String opct = scan.nextLine();
                opc_principal = Integer.parseInt(opct);

                if (opc_principal == 1) {
                    while(opc1!=4){
                        try{
                            System.out.println("ola digite qual tipo de usuario quer cadastrar");
                            System.out.println("1 refugiado");
                            System.out.println("2 voluntario");
                            System.out.println("3 doadores");
                            System.out.println("4 sair");                          
                            String opc1t = scan.nextLine();
                            opc1 = Integer.parseInt(opc1t);
                            if(opc1==1){
                        
                        
                            }else if(opc1==2){
                        
                            }else if(opc1==3){
                                System.out.println("deseja cadastra um doador (S)im ou (N)ao");
                                String s_n= scan.nextLine();
                                while(s_n.equals("sim") || s_n.equals("s")){
                            
                            
                            
                             System.out.println("deseja cadastra outro doador (S)im ou (N)ao");
                             s_n= scan.nextLine();
                                    
                        
                                }
                        
                            }else{
                                System.out.println("Erro, digite um numero valido");
                            }
                        }catch(NumberFormatException e){
                             System.out.println("Error, digite um numero inteiro valido, por favor");
                        }
                    }

                } else if (opc_principal == 2) {

                } else if (opc_principal == 3) {

                } else if (opc_principal == 4) {

                } else if (opc_principal == 5) {

                } else if (opc_principal == 6) {
                    System.out.println("Obrigado por utilizar nosso sistema");
                } else {
                    System.out.println("Digite uma opcao valida,por favor");
                }

            } catch (NumberFormatException e) {
                System.out.println("Error, digite um numero inteiro valido, por favor");
            }

        }
        scan.close();

    }
}
