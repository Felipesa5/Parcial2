CREATE TABLE cuentas (
	id_cuenta SERIAL PRIMARY KEY,
	numero_cuenta VARCHAR(20),
	tipo_cuenta VARCHAR(30),
	saldo_cuenta DECIMAL DEFAULT 0
);

INSERT INTO cuentas (numero_cuenta, tipo_cuenta, saldo_cuenta)
VALUES ('1234-3', 'Ahorros', 100000),
		('54353-1', 'Corriente', 0);

CREATE TABLE cuentas (
	id_cuenta SERIAL PRIMARY KEY,
	numero_cuenta VARCHAR(20),
	tipo_cuenta VARCHAR(30),
	saldo_cuenta DECIMAL DEFAULT 0
);


ALTER TABLE cuentas
ADD COLUMN fk_cliente_cuenta BIGINT,
ADD CONSTRAINT fk_cliente_cuenta
FOREIGN KEY (fk_cliente_cuenta)
REFERENCES clientes(id_cliente);

ALTER TABLE clientes
RENAME COLUMN saldo_cuenta TO nombre_cliente;

INSERT INTO clientes (cedula_clientes, nombre_cliente)
VALUES 	('1060596296', 'Andres Felipe Sanchez');

		