package com.java.motor;

public class CorrectorDeCadaVuelta { // Este tio empieza la ronda de correcciones cuando la red ha dado una vuelta

	// Calcula cuánto se ha equivocado la predicción
	public double calcularPerdida(double[] loQueTeniaQueSalir, double[] loQueHaSalido) {
		double suma = 0;
		for (int i = 0; i < loQueTeniaQueSalir.length; i++) {
			double error = loQueHaSalido[i] - loQueTeniaQueSalir[i];
			suma += 0.5 * error * error;
		}
		return suma / loQueTeniaQueSalir.length;
	}

	// Es la que inicia el Backprop. Devuevle un vector con el gradiente de perdida
	// respecto a cada salida de la red
	public double[] gradienteRespectoAlIntento(double[] loQueTeniaQueSalir, double[] loQueHaSalido) {
		double[] gradiente = new double[loQueTeniaQueSalir.length];
		for (int i = 0; i < loQueTeniaQueSalir.length; i++) {
			gradiente[i] = (loQueHaSalido[i] - loQueTeniaQueSalir[i]) / loQueTeniaQueSalir.length;
		}
		return gradiente;
	}
}
