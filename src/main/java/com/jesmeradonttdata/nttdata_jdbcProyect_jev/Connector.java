package com.jesmeradonttdata.nttdata_jdbcProyect_jev;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/**
 * 
 * Clase principal de nuestro ejercicio de JDBC con conexión a MySQL.
 * 
 * @author jesmerad
 *
 */
public class Connector {

	/**
	 * Definimos las variables de conexión.
	 */
	private final String dbURL = "jdbc:mysql://localhost:3306/nttdata_javajdbc";
	private final String username = "root";
	private final String password = "";

	/**
	 * Constructor por defecto.
	 */
	public Connector() {
		super();
	}

	/**
	 * Método encargado de la conexión.
	 */
	private void connectionDB() {

		Scanner sc = new Scanner(System.in);

		// Establecemos la conexión dentro de nuestro try, para no tener la necesidad
		// cerrar la conexión al finalizar.
		// De esta manera en cuanto salgamos del try, la conexión se cerrará
		// automaticamente.
		try (Connection con = DriverManager.getConnection(dbURL, username, password)) {

			try {

				System.out.println("¡¡BIENVENIDO!!");
				System.out.println("Nuestros Jugadores: ");
				showPlayers(con);
				while (true) {
					System.out.println(
							"-------------------------------------------------------------------------------------------");
					System.out.println("¿Qué acción desea realizar?");
					showMenu();
					int num = sc.nextInt();

					switch (num) {
					case 1:
						showPlayers(con);
						break;
					case 2:
						showTeams(con);
						break;

					case 3:
						addPlayer(con, sc);
						break;
					case 4:
						upgradePlayer(con, sc);
						break;
					case 5:
						deletePlayer(con, sc);
						break;
					default:
						exit();
						break;
					}

				}

			} catch (Exception e) {
				// TODO: handle exception
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Método encargado de mostrar el menú.
	 */
	public void showMenu() {

		System.out.println("1- Mostrar Jugadores");
		System.out.println("2- Mostrar Equipos");
		System.out.println("3- Añadir Jugador");
		System.out.println("4- Modificar Jugador");
		System.out.println("5- Eliminar Jugador");
		System.out.println("6- Salir");

	}

	/**
	 * 
	 * Método encargado de insertar un nuevo jugador en nuestra lista.
	 * 
	 * @param con
	 * @param id_soccer_team
	 */
	public void insertPlayer(Connection con, Player player) {

		try {

			// Definimos la consulta que vamos a realizar.
			String sql = "INSERT INTO nttdata_mysql_soccer_player(name, birth_date, first_rol, second_rol, id_soccer_team) VALUES (?,?,?,?,?)";

			// Creamos el preparedStatement para ejecutar nuestra consulta.
			PreparedStatement statement = con.prepareStatement(sql);

			// Añadimos los elementos.
			statement.setString(1, player.getName());
			statement.setDate(2, player.getDate());
			statement.setString(3, player.getFirst_rol());
			statement.setString(4, player.getSecond_rol());
			statement.setInt(5, player.getId_soccer_team());

			// Obetnemos cuantas filas se han insertado.
			int rowsInserted = statement.executeUpdate();

			// Comprobamos que se ha añadido alguna fila.
			if (rowsInserted > 0) {
				System.out.println("Nuevo usuario insertado");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * Método encargado de crear el jugador a insertar.
	 * 
	 * @param sc
	 */
	public void addPlayer(Connection con, Scanner sc) {
		try {

			System.out.println("Escribe el nombre del jugador: ");
			String name = sc.next();
			System.out.println("Escribe el año de nacimiento: ");
			int year = sc.nextInt();
			System.out.println("Escribe el mes de nacimiento: ");
			int month = sc.nextInt();
			System.out.println("Escribe el dia de nacimiento: ");
			int day = sc.nextInt();
			System.out.println("Escribe el rol principal del jugador: ");
			String first_rol = sc.next();
			System.out.println("Escribe el rol secundario del jugador: ");
			String second_rol = sc.next();
			System.out.println(
					"Escribe el id de su equipo(Si introduce un id erróneo, el jugador se quedará sin equipo, pobre...): ");
			showTeams(con);
			int team = sc.nextInt();

			Player player = new Player(name, new Date(year - month - day), first_rol, second_rol, team);

			insertPlayer(con, player);

		} catch (Exception e) {
			System.err.println("[ERROR] Algunos de lo valores introducidos son incorrectos.");
		}

	}

	/**
	 * 
	 * Método encargado de mostrar todos los jugadores.
	 * 
	 * @param con
	 */
	public void showPlayers(Connection con) {

		// Definimos la consultar a realizar.
		String sql = "SELECT * FROM nttdata_mysql_soccer_player";

		// Instanciamos nuestro statement.
		try (Statement statement = con.createStatement()) {

			// Extraemos en la variable result, la respuesta de nuestra consulta.
			ResultSet result = statement.executeQuery(sql);

			int count = 1;

			// Iteramos nuestra variable para obtener todos los valores extraídos.
			while (result.next()) {
				String name = result.getString(2);
				Date date = result.getDate(3);
				String first_rol = result.getString(4);
				String second_rol = result.getString(5);

				String output = "%d - %s - %s - %s - %s";
				System.out.println(String.format(output, count++, name, date, first_rol, second_rol));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * Método encargado de actualizar jugadores.
	 * 
	 * @param con
	 */
	public void updatePlayer(Connection con, String name, String first_rol, String second_rol) {

		// Definimos la consulta a realizar.
		String sql = "UPDATE nttdata_mysql_soccer_player SET first_rol=?, second_rol=? WHERE name=?";

		try (PreparedStatement statement = con.prepareStatement(sql)) {

			// Añadimos los elementos.
			statement.setString(1, first_rol);
			statement.setString(2, second_rol);
			statement.setString(3, name);

			// Obetnemos cuantas filas se han insertado.
			int rowsUpdated = statement.executeUpdate();

			// Comprobamos que se ha añadido alguna fila.
			if (rowsUpdated > 0) {
				System.out.println("El usuario se ha actualizado con éxito!!");
			} else {
				System.out.println("El usuario no ha podido ser actualizado");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * Método encargado de añadir los nuevos valores.
	 * 
	 * @param con
	 * @param sc
	 */
	public void upgradePlayer(Connection con, Scanner sc) {

		try {

			System.out.println("Escribe el nombre del jugador que desea modificar: ");
			showPlayers(con);
			String name = sc.next();
			System.out.println("Escribe el nuevo rol principal: ");
			String first_rol = sc.next();
			System.out.println("Escribe el nuevo rol secundario: ");
			String second_rol = sc.next();

			updatePlayer(con, name, first_rol, second_rol);

		} catch (Exception e) {
			System.err.println("[ERROR] Algunos de lo valores introducidos son incorrectos.");
		}

	}

	/**
	 * 
	 * Método encargado de eliminar jugadores.
	 * 
	 * @param con
	 * @param player
	 */
	public void removePlayer(Connection con, String name) {

		// Definimos la consulta a realizar.
		String sql = "DELETE FROM nttdata_mysql_soccer_player WHERE name=?";

		try (PreparedStatement statement = con.prepareStatement(sql)) {

			statement.setString(1, name);

			// Obetnemos cuantas filas se han insertado.
			int rowsDelete = statement.executeUpdate();

			// Comprobamos que se ha añadido alguna fila.
			if (rowsDelete > 0) {
				System.out.println("El usuario ha sido elminiado con éxito!");
			} else {
				System.out.println("No se ha podido eliminar el usuario seleccionado.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void deletePlayer(Connection con, Scanner sc) {
		try {

			System.out.println("¿Qué jugador desea eliminar?");
			showPlayers(con);
			String name = sc.next();

			removePlayer(con, name);

		} catch (Exception e) {
			System.err.println("[ERROR] Alguno de los valores introducidos es erróneo.");
		}
	}

	/**
	 * 
	 * Método encargado de mostrar los equipos.
	 * 
	 * @param con
	 * @return
	 */
	public void showTeams(Connection con) {

		// Definimos la consultar a realizar.
		String sql = "SELECT * FROM nttdata_mysql_soccer_team";

		// Instanciamos nuestro statement.
		try (Statement statement = con.createStatement()) {

			// Extraemos en la variable result, la respuesta de nuestra consulta.
			ResultSet result = statement.executeQuery(sql);

			int count = 1;

			// Iteramos nuestra variable para obtener todos los valores extraídos.
			while (result.next()) {
				String name = result.getString(2);
				String stadium = result.getString(3);
				String city = result.getString(8);

				String output = "%d - %s - %s - %s ";
				System.out.println(String.format(output, count++, name, stadium, city));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método para salir de la app.
	 */
	public void exit() {
		System.out.println("HASTA PRONTO!!");
		System.exit(0);
	}

	/**
	 * 
	 * Main.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Connector c = new Connector();

		c.connectionDB();
	}

}
