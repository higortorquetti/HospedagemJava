package packge;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {

    public static void main(String[] args) {

        try (Connection connection = DatabaseManager.getConnection()) {

            createHospedeTable(connection);
            createQuartoTable(connection);
            createReservaTable(connection);

            System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createHospedeTable(Connection connection) throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS hospede (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "cpf TEXT NOT NULL" +
                ")";
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        }
    }

    private static void createQuartoTable(Connection connection) throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS quarto (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tipoQuarto TEXT NOT NULL," +
                "capacidadeQuarto INTEGER NOT NULL," +
                "valorDiaria REAL NOT NULL" +
                ")";
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        }
    }

    private static void createReservaTable(Connection connection) throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS reserva (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idHospede INTEGER NOT NULL," +
                "idQuarto INTEGER NOT NULL," +
                "qntdDias INTEGER NOT NULL," +
                "valorTotal REAL NOT NULL," +
                "FOREIGN KEY (idHospede) REFERENCES hospede(id)," +
                "FOREIGN KEY (idQuarto) REFERENCES quarto(id)" +
                ")";
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        }
    }
}
