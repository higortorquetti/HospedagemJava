package packge;

import java.sql.*;
import java.util.Scanner;

public class Hospede {

    static void listarHospede(Connection connection) {
        System.out.println("Lista de Hospedes");
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id, nome FROM hospede")) {

            while (resultSet.next()) {
                System.out.println("Nome: " + resultSet.getString("nome") + " ID: " + resultSet.getInt("id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void cadastrarHospede(Scanner scanner, Connection connection) {
        System.out.print("Nome do Hospede: ");
        String nome = scanner.next();
        System.out.print("CPF do Hospede: ");
        String cpf = scanner.next();

        String insertQuery = "INSERT INTO hospede (nome, cpf) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)){
            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, cpf);
            preparedStatement.executeUpdate();
            System.out.println("Hospede cadastrado com sucesso!");

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Erro ao cadastrar hospede");
        }

    }
}
