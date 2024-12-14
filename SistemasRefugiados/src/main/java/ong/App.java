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

        while (opc_principal != 8) {
            try {

                System.out.println("-------Menu de Opcoes--------");
                System.out.println("|1-Cadastro de usuarios     |");
                System.out.println("|2-Registrar doacoes        |");
                System.out.println("|3-Consultar Usuarios       |");
                System.out.println("|4-Listagem de Usuarios     |");
                System.out.println("|5-Editar Cadastros         |");
                System.out.println("|6-Excluir Cadastros        |");
                System.out.println("|7-Relatorio                |");
                System.out.println("|8-Sair                     |");
                System.out.println("-----------------------------");
                String opct = scan.nextLine();
                opc_principal = Integer.parseInt(opct);

                if (opc_principal == 1) { //CADASTROS
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

                } else if (opc_principal == 2) {//DOAÇÕES
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
                            boolean verc = d1.verificar(email_ins2, dd_aux_id);

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
                            System.out.println("Deseja fazer outra doaçao (S)im ou (N)ao");
                            s_n2 = scan.nextLine();

                        } catch (NumberFormatException e) {
                            System.out.println("erro id so pode ter numeros tente novamente");

                            System.out.println("Deseja cadastrar outro Voluntario (S)im ou (N)ao:");
                            s_n2 = scan.nextLine();

                        }

                    }

                } else if (opc_principal == 3) { //CONSULTAS
                    try {

                        System.out.println("Digite qual usuario deseja consultar ");
                        for (int i = 0; i < tipo_usu.length; i++) {
                            System.out.println((i + 1) + "- " + tipo_usu[i]);
                        }
                        System.out.println("4-voltar");
                        String opc3 = scan.nextLine();
                        int opc_c = Integer.parseInt(opc3);

                        switch (opc_c) {
                            case 1:
                                System.out.println("digite o email do doador para realizar a consulta");
                                String email_c = scan.nextLine();
                                Doadores d_c = new Doadores(null, null, null, null);
                                d_c.listar(email_c);
                                break;
                            case 2:
                                System.out.println("digite o email do Voluntario para realizar a consulta");
                                String email_v = scan.nextLine();
                                Voluntarios v_l = new Voluntarios(null, null, null, null, null);
                                v_l.listar(email_v);
                                break;
                            case 3:
                                System.out.println("Para Consultar um cadastro de Refugiado:");
                                System.out.println("Digite seu id de usuario(caso nao saiba verifique seu id na listagem)");
                                int id_alt = scan.nextInt();
                                scan.nextLine();
                                Refugiados rver = new Refugiados(null, null, null, null);
                                Boolean verc = rver.verificar(id_alt);
                                if (verc) {

                                    Refugiados r_consu = new Refugiados(null, null, null, null);
                                    //r_consu.setRefugiados_id(id_alt);
                                    r_consu.listar(id_alt);
                                }
                                break;
                            case 4:
                                System.out.println("...");
                                break;
                            default:
                                System.out.println("erro");
                                break;
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("por favor digite um numero valido");
                    }

                } else if (opc_principal == 4) { //LISTAGENS
                    try {
                        for (;;) {

                            System.out.println("ola digite qual tipo de usuario deseja listar");
                            for (int i = 0; i < tipo_usu.length; i++) {
                                System.out.println((i + 1) + "-" + tipo_usu[i]);
                            }
                            System.out.println("4 listar tudo");
                            System.out.println("5 sair da listagem");
                            String opc3 = scan.nextLine();
                            int opc3t = Integer.parseInt(opc3);

                            if (opc3t == 1) {
                                Doadores d2 = new Doadores(null, null, null, null);
                                d2.listar();

                            } else if (opc3t == 2) {
                                Voluntarios v = new Voluntarios(null, null, null, null, null);
                                v.listar();

                            } else if (opc3t == 3) {
                                Refugiados l = new Refugiados(null, null, null, null);
                                l.listar();

                            } else if (opc3t == 4) {
                                Usuarios uso = new Usuarios(null, null, null);
                                uso.listar();

                            } else if (opc3t == 5) {
                                System.out.println("...");
                                break;

                            } else {
                                System.out.println("digite um numero valido");

                            }

                        }

                    } catch (NumberFormatException e) {
                        System.out.println("por favor digite um numero valido");
                    }

                } else if (opc_principal == 5) { //ALTERAÇÕES
                    int opc4 = 0;
                    while (opc4 != 4) {

                        try {
                            System.out.println("Digite qual usuario deseja alterar ");
                            for (int i = 0; i < tipo_usu.length; i++) {
                                System.out.println((i + 1) + "- " + tipo_usu[i]);
                            }
                            System.out.println("4- sair da alteracao");
                            String opt = scan.nextLine();
                            opc4 = Integer.parseInt(opt);

                            if (opc4 == 1) {
                                System.out.println("deseja alterar um doador (S)im ou (N)ao ");
                                String cont = scan.nextLine();
                                while (cont.equals("sim") || cont.equals("s")) {
                                    System.out.println("Para Alterar por favor se indentifiquesse ");

                                    System.out.println("Digite seu email de doador");
                                    String emailalt = scan.nextLine();
                                    System.out.println("Digite seu id");
                                    int id_alt = scan.nextInt();
                                    scan.nextLine();
                                    Doadores dver = new Doadores(null, null, null, emailalt);
                                    Boolean verc = dver.verificar(emailalt, id_alt);
                                    if (verc) {
                                        System.out.println("ok agora preencha os campos abaixo para alteraçao");
                                        System.out.println("Alteraçao de nome:");
                                        String nomealt = scan.nextLine();
                                        System.out.println("Alteraçao de nacionalidade:");
                                        String nacio = scan.nextLine();
                                        System.out.println("Alteraçao na data de nascimento");
                                        String data_n = scan.nextLine();
                                        System.out.println("ALteraçao de email:");
                                        String email_alt = scan.nextLine();
                                        Doadores d_alterar = new Doadores(nomealt, data_n, nacio, email_alt);
                                        d_alterar.setDoadores_id(id_alt);
                                        d_alterar.alterar();

                                    }
                                    System.out.println("deseja alterar novamente (S)im ou (N)ao ?");
                                    cont = scan.nextLine();
                                }

                            } else if (opc4 == 2) {
                                System.out.println("deseja alterar um voluntario (S)im ou (N)ao ");
                                String cont = scan.nextLine();
                                while (cont.equals("sim") || cont.equals("s")) {
                                    System.out.println("Para Alterar por favor se indentifiquesse ");

                                    System.out.println("Digite seu email de voluntario");
                                    String emailalt = scan.nextLine();
                                    System.out.println("Digite seu id");
                                    int id_alt = scan.nextInt();
                                    scan.nextLine();
                                    Voluntarios vver = new Voluntarios(null, null, null, emailalt, null);
                                    Boolean verc = vver.verificar(emailalt, id_alt);
                                    if (verc) {
                                        System.out.println("ok agora preencha os campos abaixo para alteraçao");
                                        System.out.println("Alteraçao de nome:");
                                        String nomealt = scan.nextLine();
                                        System.out.println("Alteraçao de nacionalidade:");
                                        String nacio = scan.nextLine();
                                        System.out.println("Alteraçao na data de nascimento");
                                        String data_n = scan.nextLine();
                                        System.out.println("ALteraçao de email:");
                                        String email_alt = scan.nextLine();
                                        System.out.println("ALteraçao de Habilidades:");
                                        String hb_alt = scan.nextLine();
                                        Voluntarios v_alterar = new Voluntarios(nomealt, data_n, nacio, email_alt, hb_alt);
                                        v_alterar.setVolu_id(id_alt);
                                        v_alterar.alterar();

                                    }
                                    System.out.println("deseja alterar novamente (S)im ou (N)ao ?");
                                    cont = scan.nextLine();
                                }

                            } else if (opc4 == 3) {
                                System.out.println("Deseja alterar um refugiado (S)im ou (N)ao ");
                                String cont = scan.nextLine();
                                while (cont.equals("sim") || cont.equals("s")) {
                                    System.out.println("Para Alterar por favor se indentifique: ");

                                    System.out.println("Digite seu id de usuario(caso nao saiba verifique seu id na listagem)");
                                    int id_alt = scan.nextInt();
                                    scan.nextLine();
                                    Refugiados rver = new Refugiados(null, null, null, null);
                                    Boolean verc = rver.verificar(id_alt);
                                    if (verc) {
                                        System.out.println("Agora preencha os campos abaixo para alteraçao:");
                                        System.out.println("Alteraçao de nome:");
                                        String nomealt = scan.nextLine();
                                        System.out.println("Alteraçao de nacionalidade:");
                                        String nacio = scan.nextLine();
                                        System.out.println("Alteraçao na data de nascimento");
                                        String data_n = scan.nextLine();
                                        System.out.println("ALteraçao de estado(legal ou ilegal):");
                                        String estado_alt = scan.nextLine();
                                        Refugiados r_alterar = new Refugiados(nomealt, data_n, nacio, estado_alt);
                                        r_alterar.setRefugiados_id(id_alt);
                                        r_alterar.alterar();

                                    }
                                    System.out.println("Deseja alterar novamente (S)im ou (N)ao ?");
                                    cont = scan.nextLine();
                                }

                            } else if (opc4 == 4) {
                                System.out.println("...");
                                break;
                            } else {
                                System.out.println("digite um numero valido por favor");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("por favor digite um numero valido");
                        }
                    }

                } else if (opc_principal == 6) { //EXCLUSÃO
                    int opc5 = 0;
                    while (opc5 != 4) {
                        try {

                            System.out.println("ola digite qual tipo de usuario deseja excluir");
                            for (int i = 0; i < tipo_usu.length; i++) {
                                System.out.println((i + 1) + "- " + tipo_usu[i]);
                            }

                            System.out.println("4- sair ");
                            String opc5t = scan.nextLine();
                            opc5 = Integer.parseInt(opc5t);

                            if (opc5 == 1) {

                                System.out.println("digite o nome do usuario que deseja excluir");
                                String nome_excl = scan.nextLine();
                                System.out.println("digite o email de doador");
                                String email_excl = scan.nextLine();
                                System.out.println("Digite o id de doador");
                                String dd_idt = scan.nextLine();
                                int dd_id = Integer.parseInt(dd_idt);

                                Doadores d3 = new Doadores(nome_excl, null, null, email_excl);
                                if (d3.verificar(email_excl, dd_id)) {
                                    d3.excluir();
                                }

                            } else if (opc5 == 2) {

                            } else if (opc5 == 3) {
                                System.out.println("Para excluir um cadastro de Refugiado:");
                                System.out.println("Digite seu id de usuario(caso nao saiba verifique seu id na listagem)");
                                int id_alt = scan.nextInt();
                                scan.nextLine();
                                Refugiados rver = new Refugiados(null, null, null, null);
                                Boolean verc = rver.verificar(id_alt);
                                if (verc) {

                                    Refugiados r_excluir = new Refugiados(null, null, null, null);
                                    r_excluir.setRefugiados_id(id_alt);
                                    r_excluir.excluir();
                                }

                            } else if (opc5 == 4) {
                                System.out.println("...");
                                break;
                            } else {
                                System.out.println("digite um numero valido por favor");
                            }

                        } catch (NumberFormatException e) {
                            System.out.println("ops algo deu errado verifique se esta preenchendo corretamente e tente novamente");

                        }
                    }
                } else if (opc_principal==7){
                    System.out.println("Crinado Relatorio");
                    System.out.println("....");
                    Doadores d7 = new Doadores("s", "0", "b","a");
                    d7.relatorio();
                    Voluntarios v7 = new Voluntarios("0","1","b","c","d");
                    v7.relatorio();
                    Refugiados r7 = new Refugiados("0","0","0","0");
                    r7.relatorio();
                    System.out.println("relatorio feito");

                } else if (opc_principal == 8) {
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
