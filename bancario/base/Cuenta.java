package bancario.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Cuenta {
    double saldo;
    String numero;
    String tipo;
    Cliente titular;

    public Cuenta(String tipo, String numero, double saldo, Cliente titular) {
        this.tipo = tipo;
        this.numero = numero;
        this.saldo = saldo;
        this.titular = titular;
    }

    

   

    public double getSaldo() {
        return saldo;
    }



    public String getNumero() {
        return numero;
    }



    public String getTipo() {
        return tipo;
    }

    public static boolean insertarCuenta(Connection connection, Cuenta cuenta) throws SQLException {
        String query = "INSERT INTO cuentas (numero_cuenta, tipo_cuenta, saldo_cuenta) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, cuenta.numero);
            stmt.setString(2, cuenta.tipo);
            stmt.setDouble(3, cuenta.saldo);
            return stmt.executeUpdate() > 0;
        }
    }

    public static Cuenta buscarCuenta(Connection connection, String numeroCuenta) throws SQLException {
        String query = "SELECT * FROM cuentas WHERE numero_cuenta = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, numeroCuenta);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String tipo = rs.getString("tipo_cuenta");
                    double saldo = rs.getDouble("saldo_cuenta");
                    // Asumiendo que el titular no está directamente en la tabla cuentas
                    return new Cuenta(tipo, numeroCuenta, saldo, null);
                }
            }
        }
        return null;
    }

    public static boolean actualizarSaldo(Connection connection, String numeroCuenta, double nuevoSaldo) throws SQLException {
        String query = "UPDATE cuentas SET saldo_cuenta = ? WHERE numero_cuenta = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, nuevoSaldo);
            stmt.setString(2, numeroCuenta);
            return stmt.executeUpdate() > 0;
        }
    }

    public static boolean eliminarCuenta(Connection connection, String numeroCuenta) throws SQLException {
        String query = "DELETE FROM cuentas WHERE numero_cuenta = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, numeroCuenta);
            return stmt.executeUpdate() > 0;
        }
    }
}
