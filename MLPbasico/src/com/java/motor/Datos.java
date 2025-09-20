package com.java.motor;

import java.util.List;

public class Datos implements DatosEntrenamiento {

	private List<double[]> entradas;
	private List<double[]> salidas;

	@Override
	public void setEntradas(List<double[]> entradas) {
		this.entradas = entradas;
	}

	@Override
	public void setSalidas(List<double[]> salidas) {
		this.salidas = salidas;
	}

	public List<double[]> getEntradas() {
		return entradas;
	}

	public List<double[]> getSalidas() {
		return salidas;
	}

}
