package com.java.motor;

public class ReLU implements Funcion {
	@Override
	public double desLinealizar(double z) {
		return Math.max(0.0, z);
	}

	@Override
	public double derivada(double z) {
		return z > 0.0 ? 1.0 : 0.0;
	}
}
