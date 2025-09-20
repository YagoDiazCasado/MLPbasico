package com.java.motor;

import java.util.List;

public interface DatosEntrenamiento {

	void setEntradas(List<double[]> entradas);

	void setSalidas(List<double[]> salidas);

	List<double[]> getEntradas();

	List<double[]> getSalidas();

}
