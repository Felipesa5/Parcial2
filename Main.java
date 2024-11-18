import bancario.OperacionesBanco;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/banco";
        String user = "postgres";
        String password = "1234";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Conexión exitosa");

            // Crear una instancia de OperacionesBanco pasando la conexión
            OperacionesBanco operacionesBanco = new OperacionesBanco(connection);

            // Mostrar los datos actuales de las tablas para confirmar la conexión
            mostrarDatosTablas(connection);

            operacionesBanco.menuOpciones();

        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }

    private static void mostrarDatosTablas(Connection connection) {
        try (Statement statement = connection.createStatement()) {

            System.out.println("Datos en la tabla 'cuentas':");
            ResultSet resultSetCuentas = statement.executeQuery("SELECT * FROM cuentas");
            while (resultSetCuentas.next()) {
                int idCuenta = resultSetCuentas.getInt("id_cuenta");
                String numeroCuenta = resultSetCuentas.getString("numero_cuenta");
                String tipoCuenta = resultSetCuentas.getString("tipo_cuenta");
                double saldo = resultSetCuentas.getDouble("saldo_cuenta");
                System.out.println("ID: " + idCuenta + ", Numero: " + numeroCuenta + ", Tipo: " + tipoCuenta + ", Saldo: " + saldo);
            }


            System.out.println("\nDatos en la tabla 'clientes':");
            ResultSet resultSetClientes = statement.executeQuery("SELECT * FROM clientes");
            while (resultSetClientes.next()) {
                int idCliente = resultSetClientes.getInt("id_cliente");
                String nombreCliente = resultSetClientes.getString("nombre_cliente");
                String cedulaCliente = resultSetClientes.getString("cedula_cliente");
                System.out.println("ID: " + idCliente + ", Nombre: " + nombreCliente + ", Cedula: " + cedulaCliente);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener datos de las tablas: " + e.getMessage());
        }
    }
}
