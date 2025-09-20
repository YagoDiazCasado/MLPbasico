package com.java.motor;

public class Sigmoid implements Funcion {
	@Override
	public double desLinealizar(double z) {
		return 1.0 / (1.0 + Math.exp(-z));
	}

	@Override
	public double derivada(double z) {
		double s = desLinealizar(z);
		return s * (1.0 - s);
	}
}