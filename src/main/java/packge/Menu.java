package packge;

import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);

    public int exibirMenuPrincipal(){
        System.out.println("");
        System.out.println("Escolha uma ação:");
        System.out.println("1. Cadastrar Hospede");
        System.out.println("2. Cadastrar Quarto");
        System.out.println("3. Cadastrar Reserva");
        System.out.println("4. Listar Reservas");
        System.out.println("5. Sair");
        return scanner.nextInt();
    }

}
