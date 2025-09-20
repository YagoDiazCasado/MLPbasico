package com.java.motor;

public class LeakyRelu implements Funcion {

	private double alpha = 0.01; // puedes tunear este valor

	@Override
	public double desLinealizar(double salida) {
		return salida >= 0 ? salida : alpha * salida;
	}

	@Override
	public double derivada(double z) {
		return z >= 0 ? 1.0 : alpha;
	}
}
