package bancario;

import javax.swing.JOptionPane;
import bancario.base.Cuenta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OperacionesBanco {
    private final Banco banco;
    private final Connection connection;

    public OperacionesBanco(Connection connection) {
        this.connection = connection; // Guardamos la conexión
        this.banco = new Banco(connection); // Pasamos la conexión al banco
    }

    public void menuOpciones() {
        JOptionPane.showMessageDialog(null, "Bienvenido al Banco Sanchez. ¿Qué deseas hacer hoy?");
        String decisionTxt = JOptionPane.showInputDialog(
                "1: Agregar Cuenta\n" +
                "2: Buscar cuenta\n" +
                "3: Actualizar saldo\n" +
                "4: Eliminar cuenta\n" +
                "5: Consultar total de dinero del banco\n" +
                "6: Consultar cliente con mayor dinero\n"
        );
        int decision = Integer.parseInt(decisionTxt);
        menu(decision);
    }

    private void menu(int num) {
        switch (num) {
            case 1: // Agregar cuenta
                String numero = JOptionPane.showInputDialog("Número de la cuenta:");
                String tipo = JOptionPane.showInputDialog("Tipo de cuenta (Ahorros/Corriente):");
                String saldoStr = JOptionPane.showInputDialog("Saldo inicial:");
                double saldo = Double.parseDouble(saldoStr);
                if (banco.adicionarCuenta(numero, saldo, tipo)) {
                    JOptionPane.showMessageDialog(null, "Cuenta creada exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al crear la cuenta.");
                }
                break;

            case 2:
                numero = JOptionPane.showInputDialog("Número de la cuenta:");
                Cuenta cuenta = banco.buscarCuenta(numero);
                if (cuenta != null) {
                    JOptionPane.showMessageDialog(null, "Cuenta encontrada:\n" +
                            "Número: " + cuenta.getNumero() + "\n" +
                            "Tipo: " + cuenta.getTipo() + "\n" +
                            "Saldo: " + cuenta.getSaldo());
                } else {
                    JOptionPane.showMessageDialog(null, "Cuenta no encontrada.");
                }
                break;

            case 3:
                numero = JOptionPane.showInputDialog("Número de la cuenta:");
                String nuevoSaldoStr = JOptionPane.showInputDialog("Nuevo saldo:");
                double nuevoSaldo = Double.parseDouble(nuevoSaldoStr);
                if (banco.actualizarSaldo(numero, nuevoSaldo)) {
                    JOptionPane.showMessageDialog(null, "Saldo actualizado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al actualizar el saldo.");
                }
                break;

            case 4:
                numero = JOptionPane.showInputDialog("Número de la cuenta:");
                if (banco.eliminarCuenta(numero)) {
                    JOptionPane.showMessageDialog(null, "Cuenta eliminada exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar la cuenta.");
                }
                break;

            case 5:
                consultarTotalDineroBanco();
                break;

            case 6:
                consultarClienteMayorDinero();
                break;

            default:
                JOptionPane.showMessageDialog(null, "Opción no válida.");
                break;
        }
    }

    private void consultarTotalDineroBanco() {
        String query = "SELECT SUM(saldo_cuenta) AS total_dinero FROM cuentas";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) { // Uso directo de `connection`
            if (resultSet.next()) {
                double totalDinero = resultSet.getDouble("total_dinero");
                System.out.println("El total de dinero en el banco es: " + totalDinero);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar el total de dinero: " + e.getMessage());
        }
    }

    private void consultarClienteMayorDinero() {
        String query = """
            SELECT c.nombre_cliente, SUM(cu.saldo_cuenta) AS total_dinero
            FROM clientes c
            JOIN cuentas cu ON c.id_cliente = c.id_cliente
            GROUP BY c.id_cliente, c.nombre_cliente
            ORDER BY total_dinero DESC
            LIMIT 1
            """;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) { // Uso directo de `connection`
            if (resultSet.next()) {
                String nombreCliente = resultSet.getString("nombre_cliente");
                double totalDinero = resultSet.getDouble("total_dinero");
                System.out.println("El cliente con mayor dinero es: " + nombreCliente + " con un total de: " + totalDinero);
            } else {
                System.out.println("No hay clientes registrados en el banco.");
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar el cliente con mayor dinero: " + e.getMessage());
        }
    }
}
