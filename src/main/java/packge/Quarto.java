package packge;

import java.sql.*;
import java.util.Scanner;

public class Quarto {

    static void listarQuartos(Connection connection) {
        System.out.println("Lista de quartos cadastrados:");
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id, tipoQuarto FROM quarto")) {

            while (resultSet.next()) {
                System.out.println("Tipo: " + resultSet.getString("tipoQuarto") + " ID: " + resultSet.getInt("id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void cadastrarQuarto(Scanner scanner, Connection connection){
        System.out.println("Qual é o tipo do quarto?");
        String tipoQuarto = scanner.next();
        System.out.println("Qual a capacidade do quarto?");
        int capacidadeQaurto = scanner.nextInt();
        System.out.println("Qual é o valor da Diaria?");
        double valorDiaria = scanner.nextDouble();

        String insertQuery = "INSERT INTO quarto (tipoQuarto, capacidadeQuarto, valorDiaria) VALUES(?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)){
            preparedStatement.setString(1, tipoQuarto);
            preparedStatement.setInt(2, capacidadeQaurto);
            preparedStatement.setDouble(3, valorDiaria);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Erro ao cadastrar Quarto");
        }
        System.out.println("Quarto cadastrado com sucesso!");
    }

}
