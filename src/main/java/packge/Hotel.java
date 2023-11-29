package packge;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import static packge.Hospede.cadastrarHospede;
import static packge.Quarto.cadastrarQuarto;
import static packge.Reserva.cadastrarReserva;
import static packge.Reserva.listarReserva;

public class Hotel {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Connection connection = DatabaseManager.getConnection();
        Menu menu = new Menu();


        while (true) {
            int escolha = menu.exibirMenuPrincipal();

            switch (escolha) {
                case 1:
                    cadastrarHospede(scanner, connection);
                    break;
                case 2:
                    cadastrarQuarto(scanner, connection);
                    break;
                case 3:
                    cadastrarReserva(scanner,connection);
                    break;
                case 4:
                    listarReserva(connection);
                    break;
                case 5:
                    System.out.println("Encerrando o Porgrama");
                    DatabaseManager.closeConnection(connection);
                    System.exit(0);
                default:
                    System.out.println("Escolha uma opção válida");
            }
        }
    }
}
