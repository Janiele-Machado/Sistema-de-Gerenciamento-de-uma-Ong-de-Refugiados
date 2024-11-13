package ong;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int opc_principal;

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
