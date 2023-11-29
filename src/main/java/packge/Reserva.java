package packge;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import static packge.Hospede.listarHospede;
import static packge.Quarto.listarQuartos;

public class Reserva {

    public static void cadastrarReserva(Scanner scanner, Connection connection){
        listarQuartos(connection);
        System.out.println("Qual quarto você deseja utilizar?");
        int opcaoQuartoId = scanner.nextInt();

        if (quartoExists(connection, opcaoQuartoId)){
            listarHospede(connection);
            System.out.println("Qual Hospede responsavel pela reserva?");
            int hospedeRespId = scanner.nextInt();

            if (hospedeExists(connection, hospedeRespId)){
                System.out.println("Quantos dias sera a reserva?");
                int qntdDias = scanner.nextInt();

                double valorTotalReserva = calcularValorReserva(connection, opcaoQuartoId, qntdDias);

                java.util.Date checkInDate = java.util.Calendar.getInstance().getTime();
                java.util.Calendar checkOutCalendar = java.util.Calendar.getInstance();
                checkOutCalendar.add(java.util.Calendar.DAY_OF_MONTH, qntdDias);
                java.util.Date checkOutDate = checkOutCalendar.getTime();

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                System.out.println("Reserva cadastrada com sucesso!");
                System.out.println("O valor total da reserva é de R$: " + valorTotalReserva);
                System.out.println("Data de Check-in: " + dateFormat.format(checkInDate));
                System.out.println("Data de Check-out: " + dateFormat.format(checkOutDate));
                registrarReserva(connection, hospedeRespId, opcaoQuartoId, qntdDias, valorTotalReserva);
            } else {
                System.out.println("Hospede não cadastrado");
            }
        }else {
            System.out.println("Quarto não cadastrado");
        }
    }

    private static boolean quartoExists(Connection connection, int opcaoQuartoId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM quarto WHERE id = ?")) {
            preparedStatement.setInt(1, opcaoQuartoId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean hospedeExists(Connection connection, int hospedeRespId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM hospede WHERE id = ?")) {
            preparedStatement.setInt(1, hospedeRespId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static double calcularValorReserva(Connection connection, int opcaoQuartoId, int qntdDias) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT valorDiaria FROM quarto WHERE id = ?")) {
            preparedStatement.setInt(1, opcaoQuartoId);
            ResultSet resultSet = preparedStatement.executeQuery();

            double valorDiaria = 0;
            if (resultSet.next()) {
                valorDiaria = resultSet.getDouble("valorDiaria");
            }

            return valorDiaria * qntdDias;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static void registrarReserva(Connection connection, int idHospede, int idQuarto, int qntdDias, double valorTotal) {
        String insertQuery = "INSERT INTO reserva (idHospede, idQuarto, qntdDias, valorTotal) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, idHospede);
            preparedStatement.setInt(2, idQuarto);
            preparedStatement.setInt(3, qntdDias);
            preparedStatement.setDouble(4, valorTotal);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao cadastrar reserva.");
        }
    }

    static void listarReserva( Connection connection){
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT COUNT(reserva.id) AS total_reservations, hospede.nome " +
                        "FROM reserva " +
                        "INNER JOIN hospede ON reserva.idHospede = hospede.id " +
                        "GROUP BY hospede.nome"
        )) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int totalReservations = resultSet.getInt("total_reservations");
                String hospedeName = resultSet.getString("nome");

                System.out.println("Hospede: " + hospedeName + ", Total de Reservas: " + totalReservations);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
