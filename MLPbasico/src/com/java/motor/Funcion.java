package com.java.motor;

public interface Funcion {

	public double desLinealizar(double salida);

	double derivada(double z); // esta es la derivada de la de arriba. Para el Backprop
	// las derivadas miden la razon de cambio la funcion original dado un punto, z.
	// Ese punto es el corte a la pendiente de la recta tangente a la funcion
	// original.

}
