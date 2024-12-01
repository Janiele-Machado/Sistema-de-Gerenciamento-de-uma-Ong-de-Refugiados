package ong;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;
import static javax.management.Query.or;

public class App {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int opc_principal;
        int opc1;
        int id_dd_v;
        String habilidade_ins;
        String estado_ins;
        String email_ins;
        String nome_ins;
        String nacionalidade_ins;
        String datanasc_ins;
        String doacoes_ins = "dinheiro";
        float doecoesquant_ins = 0;
        String data;
        String[] tipo_usu = {"Doadores", "Voluntarios", "Refugiados"};

        opc_principal = 0;

        while (opc_principal != 6) {
            try {

                System.out.println("-------Menu de Opcoes--------");
                System.out.println("|1-Cadastro de usuarios     |");
                System.out.println("|2-Registrar doacoes        |");
                System.out.println("|3-Listagem de usuarios     |");
                System.out.println("|4-Editar Cadastros         |");
                System.out.println("|5-Excluir Cadastros        |");
                System.out.println("|6-Sair                     |");
                System.out.println("-----------------------------");
                String opct = scan.nextLine();
                opc_principal = Integer.parseInt(opct);

                if (opc_principal == 1) {
                    opc1 = 0;
                    while (opc1 != 4) {
                        try {
                            System.out.println("-------------------------------------------------");
                            System.out.println("|Ola digite qual tipo de usuario quer cadastrar:|");
                            System.out.println("|1-Refugiado                                    |");
                            System.out.println("|2-Voluntario                                   |");
                            System.out.println("|3-Doadores                                     |");
                            System.out.println("|4-Sair                                         |");
                            System.out.println("-------------------------------------------------");
                            String opc1t = scan.nextLine();
                            opc1 = Integer.parseInt(opc1t);

                            if (opc1 == 1) {

                                System.out.println("Deseja cadastrar um refugiado (S)im ou (N)ao");
                                String s_n = scan.nextLine();

                                while (s_n.equals("sim") || s_n.equals("s")) {
                                    System.out.println("Digite o seu nome por favor:");
                                    nome_ins = scan.nextLine();
                                    System.out.println("Digite sua data de nascimento por favor:");
                                    datanasc_ins = scan.nextLine();
                                    System.out.println("Digite sua nacionalidade por favor:");
                                    nacionalidade_ins = scan.nextLine();
                                    System.out.println("Digite seu estado(Ilegal ou Legal) por favor:");
                                    estado_ins = scan.nextLine();
                                    Refugiados refugiado = new Refugiados(nome_ins, datanasc_ins, nacionalidade_ins, estado_ins);
                                    refugiado.inserir();

                                    System.out.println("Deseja cadastrar outro refugiado (S)im ou (N)ao:");
                                    s_n = scan.nextLine();

                                }

                            } else if (opc1 == 2) {

                                System.out.println("Deseja cadastrar um voluntario (S)im ou (N)ao");
                                String s_n = scan.nextLine();

                                while (s_n.equals("sim") || s_n.equals("s")) {
                                    System.out.println("Digite o seu nome por favor:");
                                    nome_ins = scan.nextLine();
                                    System.out.println("Digite sua data de nascimento por favor:");
                                    datanasc_ins = scan.nextLine();
                                    System.out.println("Digite sua nacionalidade por favor:");
                                    nacionalidade_ins = scan.nextLine();
                                    System.out.println("Digite seu email por favor:");
                                    email_ins = scan.nextLine();
                                    System.out.println("Digite sua Habilidade(Exemplo:Forca,conhecimento de medicina e etc):");
                                    habilidade_ins = scan.nextLine();
                                    Voluntarios voluntario = new Voluntarios(nome_ins, datanasc_ins, nacionalidade_ins, email_ins, habilidade_ins);
                                    voluntario.inserir();

                                    System.out.println("Deseja cadastrar outro Voluntario (S)im ou (N)ao:");
                                    s_n = scan.nextLine();

                                }

                            } else if (opc1 == 3) {
                                System.out.println("Deseja cadastrar um doador (S)im ou (N)ao");
                                String s_n = scan.nextLine();
                                while (s_n.equals("sim") || s_n.equals("s")) {
                                    System.out.println("digite o seu nome por favor");
                                    nome_ins = scan.nextLine();
                                    System.out.println("digite seu email por favor");
                                    email_ins = scan.nextLine();
                                    System.out.println("digite sua data de nascimento por favor");
                                    datanasc_ins = scan.nextLine();
                                    System.out.println("digite sua nacionalidade por favor");
                                    nacionalidade_ins = scan.nextLine();
                                    Doadores doador = new Doadores(nome_ins, datanasc_ins, nacionalidade_ins, email_ins);
                                    doador.inserir();

                                    System.out.println("Deseja cadastrar outro doador (S)im ou (N)ao");
                                    s_n = scan.nextLine();

                                }

                            } else if (opc1 == 4) {
                                System.out.println("Direcionando voce para o menu principal,Obrigada");
                            } else {
                                System.out.println("Erro, digite um numero valido");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error, digite um numero inteiro valido, por favor");
                        }
                    }

                } else if (opc_principal == 2) {
                    System.out.println("Deseja fazer uma doaçao (S)im ou (N)ao");
                    String s_n2 = scan.nextLine();
                    while (s_n2.equals("sim") || s_n2.equals("s")) {

                        try {
                            System.out.println("Ola para doar digite seu email");
                            String email_ins2 = scan.nextLine();
                            System.out.println("digite seu id");
                            String dd_aux_idt = scan.nextLine();
                            int dd_aux_id = Integer.parseInt(dd_aux_idt);

                            // futuramente farei um metodo para verificar 
                            Doadores d1 = new Doadores(null, null, null, email_ins2);
                            boolean verc = d1.verifivar(email_ins2, dd_aux_id);

                            if (verc == true) {
                                System.out.println("digite o tipo de doaçao");
                                String tipo_d = scan.nextLine();
                                System.out.println("digite e quantidade ");
                                String quant_d = scan.nextLine();
                                System.out.println("digite a data da doaçao");
                                String date_d = scan.nextLine();
                                Doacoes doacoes = new Doacoes(tipo_d, quant_d, date_d, dd_aux_id);
                                doacoes.inserir();
                            } else {
                                System.out.println("ops algo deu errado");
                            }

                        } catch (NumberFormatException e) {
                            System.out.println("erro id so pode ter numeros tente novamente");

                            System.out.println("Deseja cadastrar outro Voluntario (S)im ou (N)ao:");
                            s_n2 = scan.nextLine();

                        }

                    }

                } else if (opc_principal == 3) {
                    try {
                        for (;;) {

                            System.out.println("ola digite qual tipo de usuario deseja listar");
                            for (int i = 0; i < tipo_usu.length; i++) {
                                System.out.println((i + 1) + "-" + tipo_usu[i]);
                            }
                            System.out.println("4 sair da listagem");
                            String opc3 = scan.nextLine();
                            int opc3t = Integer.parseInt(opc3);

                            if (opc3t == 1) {
                                Doadores d2 = new Doadores(null, null, null, null);
                                d2.listar();

                            } else if (opc3t == 2) {

                            } else if (opc3t == 3) {

                            } else if (opc3t == 4) {
                                System.out.println("...");
                                break;
                            } else {
                                System.out.println("digite um numero valido");

                            }

                        }

                    } catch (NumberFormatException e) {
                        System.out.println("por favor digite um numero valido");
                    }

                } else if (opc_principal == 4) {

                } else if (opc_principal == 5) {
                    try{

                    System.out.println("ola digite qual tipo de usuario deseja excluir");
                    for (int i = 0; i < tipo_usu.length; i++) {
                        System.out.println((i + 1) + "- " + tipo_usu[i]);
                    }

                    System.out.println("4 sair ");
                    String opc5t = scan.nextLine();
                    int opc5 = Integer.parseInt(opc5t);
                    

                    if (opc5 == 1) {
                        System.out.println("digite o nome do usuario que deseja excluir");
                        String nome_excl = scan.nextLine();
                        System.out.println("digite o email de doador");
                        String email_excl = scan.nextLine();
                        System.out.println("Digite o id de doador");
                        String dd_idt = scan.nextLine();
                        int dd_id = Integer.parseInt(dd_idt);

                        Doadores d3 = new Doadores(nome_excl, null, null, email_excl);
                        if (d3.verifivar(email_excl, dd_id)) {
                            d3.excluir();
                        }

                    }
                    
                   }catch(NumberFormatException e){
                        System.out.println("ops algo deu errado verifique se esta preenchendo corretamente e tente novamente");
                       
                   }

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
