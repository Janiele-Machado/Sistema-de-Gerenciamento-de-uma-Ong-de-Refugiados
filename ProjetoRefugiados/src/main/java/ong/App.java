package ong;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int opc_principal;
        do {
            System.out.println("-----Menu de Opcoes---------");
            System.out.println("1-Cadastro de usuarios");
            System.out.println("2-Registrar doacoes");
            System.out.println("3-Listagem de usuarios ");
            System.out.println("4-Editar Cadastros");
            System.out.println("5-Excluir Cadastros");
            System.out.println("6-Sair");
            opc_principal = scan.nextInt();
            scan.nextLine();

        } while (opc_principal != 6);
    }
}
