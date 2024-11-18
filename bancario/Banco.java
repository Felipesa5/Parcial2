package bancario;

import bancario.base.Cuenta;

import java.sql.Connection;
import java.sql.SQLException;

public class Banco {
    String nombre;
    Connection connection;

    public Banco(Connection connection) {
        this.connection = connection;
    }

    public boolean adicionarCuenta(String numero, double saldoInicial, String tipo) {
        Cuenta cuenta = new Cuenta(tipo, numero, saldoInicial, null); // Sin titular para este ejemplo
        try {
            return Cuenta.insertarCuenta(connection, cuenta);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Cuenta buscarCuenta(String numero) {
        try {
            return Cuenta.buscarCuenta(connection, numero);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean actualizarSaldo(String numero, double nuevoSaldo) {
        try {
            return Cuenta.actualizarSaldo(connection, numero, nuevoSaldo);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarCuenta(String numero) {
        try {
            return Cuenta.eliminarCuenta(connection, numero);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
