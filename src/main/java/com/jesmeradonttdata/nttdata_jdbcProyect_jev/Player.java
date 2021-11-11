package com.jesmeradonttdata.nttdata_jdbcProyect_jev;

import java.sql.Date;

public class Player {

	/**
	 * Atributos de nuestra clase Player.
	 */
	private String name;
	private Date date;
	private String first_rol;
	private String second_rol;
	private Integer id_soccer_team;

	/**
	 * Constructor por defecto.
	 */
	public Player(String name, Date date, String first_rol, String second_rol, Integer id_soccer_team) {
		super();
		this.name = name;
		this.date = date;
		this.first_rol = first_rol;
		this.second_rol = second_rol;
		this.id_soccer_team = id_soccer_team;
	}
	
	/**
	 * Getters and Setters.
	 */
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFirst_rol() {
		return first_rol;
	}

	public void setFirst_rol(String first_rol) {
		this.first_rol = first_rol;
	}

	public String getSecond_rol() {
		return second_rol;
	}

	public void setSecond_rol(String second_rol) {
		this.second_rol = second_rol;
	}

	public Integer getId_soccer_team() {
		return id_soccer_team;
	}

	public void setId_soccer_team(Integer id_soccer_team) {
		this.id_soccer_team = id_soccer_team;
	}

}
